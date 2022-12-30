package org.darkSolace.muse.mail

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.darkSolace.muse.mail.model.Mail
import org.darkSolace.muse.mail.model.MailerSettings
import org.darkSolace.muse.mail.repository.MailQueueRepository
import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.testUtil.TestInit
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired

class MailServiceTests : TestBase() {
    @Autowired
    lateinit var mailService: MailService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var mailQueueRepository: MailQueueRepository

    @Autowired
    lateinit var testInit: TestInit

    @AfterEach
    fun cleanUp() {
        mailService.mailEnabled = false
        runBlocking { delay(5000) }
        greenMailExtension.purgeEmailFromAllMailboxes()
        mailQueueRepository.deleteAll()
        testInit.setupMail()
    }

    @BeforeEach
    fun setUp() {
        runBlocking { delay(5000) }
        greenMailExtension.purgeEmailFromAllMailboxes()
        mailQueueRepository.deleteAll()
        testInit.setupMail()
    }

    @Test
    @Order(1)
    fun testSendMail() {
        val user = withMailDisabled {
            userService.createUser(
                User(null, "test", "", email = "test@example.org")
            ) ?: fail("Couldn't create user")
        }

        val mail = Mail(null, user, "Test", "Test Mail")
        mailService.enqueueMail(mail)
        mailService.mailEnabled = true
        mailService.sendMails()

        runBlocking {
            delay(5_000)
        }

        val mails = greenMailExtension.getReceivedMessagesForDomain("example.org")
        val testMail = mails.firstOrNull { it.subject == "Test" } ?: fail("TestMail not found")
        Assertions.assertEquals(1, mails.size)
        Assertions.assertEquals("test@example.org", testMail.allRecipients.first().toString())
        Assertions.assertEquals("Test", testMail.subject)
        Assertions.assertEquals("Test Mail", (testMail.content as String).trim())
        Assertions.assertEquals("sender@example.org", testMail.from.first().toString())
    }

    @Test
    @Order(2)
    fun testEnqueueMessage() {
        val user = withMailDisabled {
            userService.createUser(
                User(null, "test", "", email = "test@example.org")
            ) ?: fail("Couldn't create user")
        }

        //break mail settings to simulate connection loss to the email server
        mailerSettingsService.updateMailerSettings(
            MailerSettings().apply {
                host = "localhost"
                port = 1024
                username = "invalidUser"
                password = "pass"
                fromAddress = "invalid@example.org"
            }
        )

        val mail = Mail(null, user, "Test", "Test Mail")
        mailService.enqueueMail(mail)

        runBlocking {
            delay(20000)
        }

        val mails = greenMailExtension.getReceivedMessagesForDomain("example.org")
        Assertions.assertEquals(0, mails.size)
        Assertions.assertEquals(1, mailQueueRepository.count())
        Assertions.assertTrue(mailQueueRepository.findAll().first().mail?.recipient?.email == "test@example.org")
    }

    @Test
    @Order(3)
    fun testResendEnqueuedMails() {
        val user = withMailDisabled {
            userService.createUser(
                User(null, "test", "", email = "test@example.org")
            ) ?: fail("Couldn't create user")
        }

        //break mail settings to simulate connection loss to the email server
        mailerSettingsService.updateMailerSettings(
            MailerSettings().apply {
                host = "localhost"
                port = 1024
                username = "invalidUser"
                password = "pass"
                fromAddress = "invalid@example.org"
            }
        )

        val mail = Mail(null, user, "Test", "Test Mail")
        mailService.enqueueMail(mail)

        runBlocking {
            delay(3_000)
        }

        var mails = greenMailExtension.getReceivedMessagesForDomain("example.org")
        Assertions.assertEquals(0, mails.size)
        Assertions.assertEquals(1, mailQueueRepository.count()) //unsent test mail

        //fix mail settings
        testInit.setupMail()
        //is disabled by testInit, so re-enabling it
        mailService.mailEnabled = true
        //resend mails
        runBlocking {
            delay(5_000)
            mailService.sendMails()

            mails = greenMailExtension.getReceivedMessagesForDomain("example.org")
            greenMailExtension.receivedMessages
            Assertions.assertEquals(1, mails.size)
            Assertions.assertEquals(0, mailQueueRepository.count())
        }
    }

    @Test
    @Order(4)
    fun testConfirmURLGeneration() {
        val user =
            userService.createUser(
                User(null, "test", "", email = "test@example.org")
            ) ?: fail("Couldn't create user")

        val url = mailService.generateEMailConfirmUrl(user, "https://example.org")

        Assertions.assertEquals("https://example.org/api/mail/confirm/${user.emailConfirmationCode}", url)
    }

    @Test
    @Order(5)
    fun testConfirmURLGeneration_noProtocol() {
        val user =
            userService.createUser(
                User(null, "test", "", email = "test@example.org")
            ) ?: fail("Couldn't create user")

        val url = mailService.generateEMailConfirmUrl(user, "example.org")

        Assertions.assertEquals("https://example.org/api/mail/confirm/${user.emailConfirmationCode}", url)
    }


    private fun withMailDisabled(block: () -> User): User {
        val user = block()
        Thread.sleep(5000)
        mailQueueRepository.deleteAll()
        greenMailExtension.purgeEmailFromAllMailboxes()
        return user
    }
}
