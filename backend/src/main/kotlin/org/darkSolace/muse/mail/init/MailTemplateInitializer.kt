package org.darkSolace.muse.mail.init

import org.darkSolace.muse.mail.model.MailTemplate
import org.darkSolace.muse.mail.model.MailTemplateVar
import org.darkSolace.muse.mail.model.TemplateType
import org.darkSolace.muse.mail.repository.MailTemplateRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class MailTemplateInitializer : ApplicationRunner {
    @Autowired
    private lateinit var mailTemplateRepository: MailTemplateRepository

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun run(args: ApplicationArguments?) {
        if (mailTemplateRepository.count() == 0L) {
            createSignUpMailConfirmTemplate()
            createPasswordResetTemplate()
            logger.info("Default Mail Templates created")
        } else {
            logger.info("Mail Templates already exists. Skip creating...")
        }
    }

    private fun createSignUpMailConfirmTemplate() {
        val template = MailTemplate(TemplateType.SIGN_UP_CONFIRMATION).apply {
            mailContent = """
                Hey <user>,
                
                you have created a new account at <site_name> (<site_url>).
                To complete your registration please confirm your email address by clicking the following link:
                <confirm_url>
                
                Welcome on board, we are glad to have you
                The <site_name>-Team

                
                If you didn't create an account no action is required and this email address will be removed from our database within one week.
            """.trimIndent()
            templateVars.addAll(
                listOf(
                    MailTemplateVar(
                        templateVar = "user",
                        description = "The registered user (username) (Filled in automatically)",
                        system_managed = true
                    ),
                    MailTemplateVar(templateVar = "site_name", description = "The name of this Website"),
                    MailTemplateVar(templateVar = "site_url", description = "The URL of this Website"),
                    MailTemplateVar(
                        templateVar = "confirm_url",
                        description = "The link to be clicked to confirm the registration (Filled in automatically)",
                        system_managed = true
                    )
                )
            )
            templateVarValues["site_name"] = "Elysian Muse"
            templateVarValues["site_url"] = "https://darksolace.org"
        }
        mailTemplateRepository.save(template)
    }

    private fun createPasswordResetTemplate() {
        val template = MailTemplate(TemplateType.FORGOT_PASSWORD).apply {
            mailContent = """
                Hey <user>,
                
                Looks like you forgot your password. No worries, just click here to select a new one
                <reset_url>
                
                The <site_name>-Team
            """.trimIndent()
            templateVars.addAll(
                listOf(
                    MailTemplateVar(
                        templateVar = "user",
                        description = "The registered user (username) (Filled in automatically)",
                        system_managed = true
                    ),
                    MailTemplateVar(
                        templateVar = "reset_url",
                        description = "The link to reset the password (Filled in automatically)",
                        system_managed = true
                    )
                )
            )
        }
        mailTemplateRepository.save(template)
    }
}