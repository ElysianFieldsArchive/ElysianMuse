package org.darkSolace.muse.mail.service

//import org.darkSolace.muse.mail.repository.MailTemplateVarRepository
import org.darkSolace.muse.mail.model.Mail
import org.darkSolace.muse.mail.model.MailQueueEntry
import org.darkSolace.muse.mail.model.TemplateType
import org.darkSolace.muse.mail.repository.MailQueueRepository
import org.darkSolace.muse.mail.repository.MailTemplateRepository
import org.darkSolace.muse.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MailService {
    @Autowired
    private lateinit var mailTemplateRepository: MailTemplateRepository

    @Autowired
    private lateinit var mailQueueRepository: MailQueueRepository

    @Autowired
    private lateinit var mailerSettingsService: MailerSettingsService

    private val varPattern = """<\w>""".toRegex()
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
            if (varToBeReplaced.system_managed) {
                when (varToBeReplaced.templateVar) {
                    "user" ->
                        content = content.replace("<${varToBeReplaced.templateVar}>", user.username)
                    "confirm_url" ->
                        content = content.replace("<${varToBeReplaced.templateVar}>", generateEMailConfirmUrl(user))
                }
            } else {
                val varValue = template.templateVarValues[varToBeReplaced.templateVar]
                varValue?.let {
                    content = content.replace("<${varToBeReplaced.templateVar}>", varValue)
                }
            }
        }

        //send mail
        sendMail(Mail(recipient = user, content = content, subject = template.mailSubject))
    }

    fun sendMail(mail: Mail) {
        //generate new mailer with current email settings
        val message = SimpleMailMessage().apply {
            from = mailerSettingsService.getSenderAddress()
            setTo(mail.recipient.email)
            subject = mail.subject
            text = mail.content
        }

        try {
            val mailer = mailerSettingsService.generateMailer()
            if (mailer != null) {
                mailer.send(message)
            } else {
                enqueueMail(mail)
            }
        } catch (e: Exception) {
            //something went wrong, enqueue mail to be sent later
            enqueueMail(mail)
        }
    }

    private fun enqueueMail(mail: Mail) {
        val queueEntry = MailQueueEntry(mail = mail)
        mailQueueRepository.save(queueEntry)
    }


    private fun generateEMailConfirmUrl(user: User): String {
        return "https://example.com/"
    }

    @Scheduled(cron = "0 * * * * *")
    fun resendEmails() {
        // every minute
        val enqueuedMails = mailQueueRepository.findAll()

        enqueuedMails.forEach {
            mailQueueRepository.delete(it)
            it.mail ?: return
            sendMail(it.mail)
        }
    }
}