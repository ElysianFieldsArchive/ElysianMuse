package org.darkSolace.muse.testUtil

import org.darkSolace.muse.mail.model.MailerSettings
import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.mail.service.MailerSettingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class TestInit : ApplicationRunner {
    @Autowired
    lateinit var mailerSettingsService: MailerSettingsService

    @Autowired
    lateinit var mailService: MailService

    override fun run(args: ApplicationArguments?) {
        setupMail()
        Locale.setDefault(Locale.US)
    }

    fun setupMail() {
        mailService.mailEnabled = false
        mailerSettingsService.updateMailerSettings(
            MailerSettings().apply {
                host = "localhost"
                port = 3025
                username = "test"
                password = "testPassword"
                fromAddress = "sender@example.org"
            }
        )
    }
}
