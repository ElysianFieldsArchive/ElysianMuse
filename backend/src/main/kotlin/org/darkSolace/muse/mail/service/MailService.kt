package org.darkSolace.muse.mail.service

import org.darkSolace.muse.mail.exception.TemplateException
import org.darkSolace.muse.mail.model.Mail
import org.darkSolace.muse.mail.model.MailQueueEntry
import org.darkSolace.muse.mail.model.TemplateType
import org.darkSolace.muse.mail.repository.MailQueueRepository
import org.darkSolace.muse.mail.repository.MailTemplateRepository
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSendException
import org.springframework.mail.SimpleMailMessage
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

/**
 * Service for all mail related tasks.
 */
@Service
class MailService(
    @Autowired
    private val mailTemplateRepository: MailTemplateRepository,

    @Autowired
    private val mailQueueRepository: MailQueueRepository,

    @Autowired
    private val mailerSettingsService: MailerSettingsService,
    @Autowired
    private val userRepository: UserRepository,
) {
    var mailEnabled = true

    private val varPattern = """<\w>""".toRegex()

    /**
     * Sends an email confirmation message to a newly registered user based on a template.
     *
     * @param user the user which email needs to be validated
     */
    fun sendEMailConfirmationMail(user: User) {
        //fetch mail template
        val template = mailTemplateRepository.findByType(TemplateType.SIGN_UP_CONFIRMATION)

        var content = template.mailContent

        val varsInContent = varPattern.findAll(content).map { it.groupValues[1] }.distinct().toList()
        if (varsInContent.any { it !in template.templateVars.map { vars -> vars.templateVar } }) {
            //unrecognized var in content - is this a problem?
        }

        //replace all variables in content - if there is something to replace them with
        template.templateVars.forEach { varToBeReplaced ->
            if (varToBeReplaced.systemManaged) {
                when (varToBeReplaced.templateVar) {
                    "user" ->
                        content = content.replace("<${varToBeReplaced.templateVar}>", user.username)
                    "confirm_url" ->
                        content = content.replace(
                            "<${varToBeReplaced.templateVar}>",
                            generateEMailConfirmUrl(user, template.templateVarValues["site_url"])
                        )
                }
            } else {
                val varValue = template.templateVarValues[varToBeReplaced.templateVar]
                varValue?.let {
                    content = content.replace("<${varToBeReplaced.templateVar}>", varValue)
                }
            }
        }
        //send mail
        enqueueMail(Mail(recipient = user, content = content, subject = template.mailSubject))
    }

    /**
     * Sends a password reset email to the specified user containing a reset code.
     *
     * @param user the user receiving the password reset mail
     * @param code the reset code to be sent in the email
     */
    fun sendPasswordResetMail(user: User, code: String) {
        //fetch mail template
        val template = mailTemplateRepository.findByType(TemplateType.FORGOT_PASSWORD)

        var content = template.mailContent

        val varsInContent = varPattern.findAll(content).map { it.groupValues[1] }.distinct().toList()
        if (varsInContent.any { it !in template.templateVars.map { vars -> vars.templateVar } }) {
            //unrecognized var in content - is this a problem?
        }

        //replace all variables in content - if there is something to replace them with
        template.templateVars.forEach { varToBeReplaced ->
            if (varToBeReplaced.systemManaged) {
                when (varToBeReplaced.templateVar) {
                    "user" ->
                        content = content.replace("<${varToBeReplaced.templateVar}>", user.username)
                    "reset_url" ->
                        content = content.replace(
                            "<${varToBeReplaced.templateVar}>",
                            generateResetPasswordUrl(code, template.templateVarValues["site_url"])
                        )
                }
            } else {
                val varValue = template.templateVarValues[varToBeReplaced.templateVar]
                varValue?.let {
                    content = content.replace("<${varToBeReplaced.templateVar}>", varValue)
                }
            }
        }
        //send mail
        enqueueMail(Mail(recipient = user, content = content, subject = template.mailSubject))
    }

    /**
     * Enqueues a mail to be sent on the next scheduler run
     *
     * @param mail the [Mail] to be enqueued
     */
    fun enqueueMail(mail: Mail) {
        /*val message = SimpleMailMessage().apply {
            from = mailerSettingsService.getSenderAddress()
            setTo(mail.recipient.email)
            subject = mail.subject
            text = mail.content
        }*/
        val mailQueueEntry = MailQueueEntry(null, mail)
        mailQueueRepository.save(mailQueueEntry)
    }


    /**
     * Generates a confirm-url used to verify an email address.
     *
     * @param user the user for which the confirm-url is created
     * @param siteUrl the base url of the current site
     *
     * @return the email confirm url
     *
     * @throws TemplateException if the site url is null
     */
    fun generateEMailConfirmUrl(user: User, siteUrl: String?): String {
        var (protocol, site) = siteUrl?.split("://")?.plus("")
            ?: throw TemplateException("Invalid site_url")
        //if site-url doesn't contain a protocol - add it
        if (!protocol.startsWith("http")) {
            site = protocol
            protocol = "https"
        }

        site = site.removeSuffix("/")

        return "$protocol://$site/api/mail/confirm/${user.emailConfirmationCode}"
    }

    /**
     * Generates a password reset url
     *
     * @param code the reset code used in the reset url
     * @param siteUrl the base url of the current site
     *
     * @return the password reset url
     *
     * @throws TemplateException if the site url is null
     */
    fun generateResetPasswordUrl(code: String, siteUrl: String?): String {
        var (protocol, site) = siteUrl?.split("://")?.plus("")
            ?: throw TemplateException("Invalid site_url")
        //if site-url doesn't contain a protocol - add it
        if (!protocol.startsWith("http")) {
            site = protocol
            protocol = "https"
        }

        site = site.removeSuffix("/")

        return "$protocol://$site/api/mail/reset/$code"
    }

    @Scheduled(cron = "*/10 * * * * *")
    fun sendMails() {
        if (mailEnabled) {
            // every 10 seconds
            val enqueuedMails = mailQueueRepository.findAll().toList()
            //remove mails currently processed
            mailQueueRepository.deleteAll(enqueuedMails)

            if (enqueuedMails.isEmpty()) return
            val mailToSimpleMailMap = enqueuedMails.mapNotNull { mailQueueEntry ->
                val message = SimpleMailMessage().apply {
                    from = mailerSettingsService.getSenderAddress()
                    setTo(mailQueueEntry.mail?.recipient?.email ?: "")
                    subject = mailQueueEntry.mail?.subject
                    text = mailQueueEntry.mail?.content
                }
                mailQueueEntry.mail to message
            }.toMap()
            try {
                val mailsToBeSent = mailToSimpleMailMap.values.toTypedArray()
                mailerSettingsService.getMailer().send(*mailsToBeSent)
            } catch (mailEx: MailSendException) {
                println("Something went wrong sending mails")
                mailEx.failedMessages.values.forEach { it.printStackTrace() }
                val failed = mailEx.failedMessages.keys

                //requeue failed mails
                val failedMails = mailToSimpleMailMap.filter { it.value in failed }
                val failedMailQueueEntries = enqueuedMails.filter { it.mail in failedMails }
                mailQueueRepository.saveAll(failedMailQueueEntries)
            }
        }
    }

    /**
     * Marks the email of the given user as validated and cleans up all data regarding the (previously outstanding)
     * validation
     *
     * @param user the [User] to update
     */
    fun markEMailAsValid(user: User) {
        user.emailConfirmed = true
        user.emailConfirmationCode = ""
        userRepository.save(user)
    }

    /**
     * Marks the email of the given user as validated and cleans up all data regarding the (previously outstanding)
     * validation
     *
     * @param username the [User] to update
     */
    fun markEMailAsValid(username: String) {
        val user = userRepository.findByUsername(username) ?: return
        markEMailAsValid(user)
    }

    fun confirmEMailByCode(code: String): Boolean {
        val user = userRepository.findByEmailConfirmationCode(code) ?: return false
        markEMailAsValid(user)
        return true
    }
}
