package org.darkSolace.muse.mail.service

import org.darkSolace.muse.mail.model.MailerSettings
import org.darkSolace.muse.mail.repository.MailerSettingsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.validation.Valid

/**
 * Service class for [MailerSettings] related tasks. Also holds the current [JavaMailSender] to be used to send EMails.
 */
@Service
class MailerSettingsService {
    @Autowired
    private lateinit var mailerSettingsRepository: MailerSettingsRepository
    private lateinit var mailer: JavaMailSender

    /**
     * Generates a new [JavaMailSender] based on the current [MailerSettings].
     *
     * @return the new [JavaMailSender] used to send mails.
     */
    private fun generateMailer(): JavaMailSender {
        val mailSettings = mailerSettingsRepository.findAll()
        val settings = mailSettings.first()
        val mailer = JavaMailSenderImpl().apply {
            host = settings.host
            port = settings.port

            username = settings.username
            password = settings.password

            protocol = settings.protocol

            if (protocol == "smtp") {
                javaMailProperties["mail.smtp.auth"] = settings.auth
                javaMailProperties["mail.smtp.starttls.enable"] = settings.startTLS
            }
        }

        return mailer
    }

    /**
     * Getter for the current [JavaMailSender]
     */
    fun getMailer(): JavaMailSender {
        return mailer
    }

    /**
     * Getter for the current sender address used when sending mails.
     * Can be changed by applying a new [MailerSettings].
     *
     * @return the sender address
     */
    fun getSenderAddress(): String {
        val settings = mailerSettingsRepository.findAll().first()
        return settings.fromAddress
    }

    /**
     * Updates the [MailerSettings] and generates a new [JavaMailSender] to be used to send mails after the update
     *
     * @param newSettings the new [MailerSettings] to be used
     */
    @Transactional
    fun updateMailerSettings(@Valid newSettings: MailerSettings) {
        //delete all settings, only one setting allowed
        mailerSettingsRepository.deleteAll()
        mailerSettingsRepository.save(newSettings)
        mailer = generateMailer()
    }

    /**
     * Getter for the currently used [MailerSettings]
     */
    fun getCurrentSettings(): MailerSettings = mailerSettingsRepository.findAll().first()
}
