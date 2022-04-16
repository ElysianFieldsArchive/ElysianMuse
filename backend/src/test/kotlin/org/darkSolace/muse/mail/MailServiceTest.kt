package org.darkSolace.muse.mail

import org.darkSolace.muse.mail.model.Mail
import org.darkSolace.muse.mail.model.MailerSettings
import org.darkSolace.muse.mail.repository.MailQueueRepository
import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.mail.service.MailerSettingsService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired


class MailServiceTest : TestBase() {
    @Autowired
    lateinit var mailService: MailService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var mailerSettingsService: MailerSettingsService

    @Autowired
    lateinit var mailQueueRepository: MailQueueRepository

    @Test
    @Order(1)
    fun testSendMail() {
        greenMailExtension.purgeEmailFromAllMailboxes()
        val mail = Mail(null, User(null, "test", "", email = "test@example.org"), "Test", "Test Mail")

        mailService.sendMail(mail)

        val mails = greenMailExtension.getReceivedMessagesForDomain("example.org")
        Assertions.assertEquals(1, mails.size)
        Assertions.assertEquals("test@example.org", mails.first().allRecipients.first().toString())
        Assertions.assertEquals("Test", mails.first().subject)
        Assertions.assertEquals("Test Mail", (mails.first().content as String).trim())
        Assertions.assertEquals("sender@example.org", mails.first().from.first().toString())
    }

    @Test
    @Order(2)
    fun testEnqueueMessage() {
        val user =
            userService.createUser(
                User(null, "test", "", email = "test@example.org")
            ) ?: fail("Couldn't create user")
        greenMailExtension.purgeEmailFromAllMailboxes()

        //break mail settings to simulate connection loss to the email server
        mailerSettingsService.updateMailerSettings(
            MailerSettings(
                null,
                "localhost",
                1024,
                "invalidUser",
                "pass",
                fromAddress = "invalid@example.org"
            )
        )

        val mail = Mail(null, user, "Test", "Test Mail")
        mailService.sendMail(mail)

        val mails = greenMailExtension.getReceivedMessagesForDomain("example.org")
        Assertions.assertEquals(0, mails.size)
        Assertions.assertEquals(1, mailQueueRepository.count())
        Assertions.assertEquals("test", mailQueueRepository.findAll().first().mail?.recipient?.username)
    }

    @Test
    @Order(3)
    fun testResendEnqueuedMails() {
        val user =
            userService.createUser(
                User(null, "test", "", email = "test@example.org")
            ) ?: fail("Couldn't create user")
        greenMailExtension.purgeEmailFromAllMailboxes()

        //break mail settings to simulate connection loss to the email server
        mailerSettingsService.updateMailerSettings(
            MailerSettings(
                null,
                "localhost",
                1024,
                "invalidUser",
                "pass",
                fromAddress = "invalid@example.org"
            )
        )

        val mail = Mail(null, user, "Test", "Test Mail")
        mailService.sendMail(mail)

        var mails = greenMailExtension.getReceivedMessagesForDomain("example.org")
        Assertions.assertEquals(0, mails.size)
        Assertions.assertEquals(1, mailQueueRepository.count())

        //fix mail settings
        setupMail()

        //resend mails
        mailService.resendEmails()
        mails = greenMailExtension.getReceivedMessagesForDomain("example.org")
        Assertions.assertEquals(1, mails.size)
        Assertions.assertEquals(0, mailQueueRepository.count())
    }
}
