package org.darkSolace.muse.mail.init

import org.darkSolace.muse.mail.model.MailerSettings
import org.darkSolace.muse.mail.repository.MailerSettingsRepository
import org.darkSolace.muse.mail.service.MailerSettingsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

/**
 * Generates dummy [MailerSettings] on startup, if no other [MailerSettings] exists.
 */
@Component
class MailSettingInitializer(
    @Autowired private val mailerSettingsService: MailerSettingsService,
    @Autowired private val mailerSettingsRepository: MailerSettingsRepository
) : ApplicationRunner {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val port = 539

    override fun run(args: ApplicationArguments?) {
        val settings = MailerSettings(
            null
        ).apply {
            host = "example.org"
            port = port
            username = "exampleUser"
            password = "examplePassword"
            fromAddress = "muse@example.org"
        }

        if (mailerSettingsRepository.count() == 0L) {
            mailerSettingsService.updateMailerSettings(settings)
            logger.info("Default Mailer-Settings created")
        } else {
            logger.info("Mailer Settings already exist. Skip creating...")
        }
    }
}
