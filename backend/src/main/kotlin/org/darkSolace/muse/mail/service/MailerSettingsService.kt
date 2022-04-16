package org.darkSolace.muse.mail.service

import org.darkSolace.muse.mail.model.MailerSettings
import org.darkSolace.muse.mail.repository.MailerSettingsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MailerSettingsService {
    @Autowired
    private lateinit var mailerSettingsRepository: MailerSettingsRepository

    fun generateMailer(): JavaMailSender? {
        val mailSettings = mailerSettingsRepository.findAll().toList()
        if (mailSettings.isNotEmpty()) {
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
        } else {
            return JavaMailSenderImpl()
        }
    }

    fun getSenderAddress(): String {
        val settings = mailerSettingsRepository.findAll().first()
        return settings.fromAddress
    }

    @Transactional
    fun updateMailerSettings(newSettings: MailerSettings) {
        //delete all settings, only one setting allowed
        mailerSettingsRepository.deleteAll()
        mailerSettingsRepository.save(newSettings)
    }
}