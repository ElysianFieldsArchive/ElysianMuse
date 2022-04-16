package org.darkSolace.muse.mail.init

import org.darkSolace.muse.mail.model.MailerSettings
import org.darkSolace.muse.mail.repository.MailerSettingsRepository
import org.darkSolace.muse.mail.service.MailerSettingsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class MailSettingInitializer(
    @Autowired private val mailerSettingsService: MailerSettingsService,
    @Autowired private val mailerSettingsRepository: MailerSettingsRepository
) : ApplicationRunner {
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun run(args: ApplicationArguments?) {
        val settings = MailerSettings(
            null,
            "example.org",
            539,
            "exampleUser",
            "examplePassword",
            fromAddress = "muse@example.org"
        )

        if (mailerSettingsRepository.count() == 0L) {
            mailerSettingsService.updateMailerSettings(settings)
            logger.info("Default Mailer-Settings created")
        } else {
            logger.info("Mailer Settings already exist. Skip creating...")
        }
    }
}