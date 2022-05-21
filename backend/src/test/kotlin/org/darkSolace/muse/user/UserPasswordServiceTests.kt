package org.darkSolace.muse.user

import org.darkSolace.muse.mail.repository.MailQueueRepository
import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.repository.PasswordResetRequestRepository
import org.darkSolace.muse.user.service.UserPasswordService
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired

class UserPasswordServiceTests : TestBase() {
    @Autowired
    lateinit var mailQueueRepository: MailQueueRepository

    @Autowired
    lateinit var passwordResetRequestRepository: PasswordResetRequestRepository

    @Autowired
    lateinit var userPasswordService: UserPasswordService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var mailService: MailService

    @Test
    @Order(1)
    fun generateAndSendPasswordResetCode() {
        val user = userService.createUser(User(username = "testUser10", password = "123", email = "test10@example.com"))
            ?: fail("User couldn't be created")
        mailService.markEMailAsValid(user)
        mailQueueRepository.deleteAll()

        userPasswordService.generateAndSendPasswordResetCode(user)

        val passwordResetRequest = passwordResetRequestRepository.findByUser(user)
        val resetMail = mailQueueRepository.findAll().first()

        Assertions.assertNotNull(passwordResetRequest)
        Assertions.assertNotNull(resetMail)
        Assertions.assertTrue(resetMail.mail?.subject?.startsWith("Reset Password for") ?: false)
        Assertions.assertEquals(user.email, resetMail.mail?.to?.first())
    }

    @Test
    @Order(2)
    fun updatePassword() {
        val user = userService.createUser(User(username = "testUser10", password = "123", email = "test10@example.com"))
            ?: fail("User couldn't be created")
        mailService.markEMailAsValid(user)

        val oldHashedPassword = user.password
        val oldSalt = user.salt

        userPasswordService.updatePassword(user, "234")

        val userFromDB = userService.getById(user.id ?: -1)

        Assertions.assertNotEquals(oldHashedPassword, userFromDB?.password)
        Assertions.assertNotEquals(oldSalt, userFromDB?.salt)
    }

    @Test
    @Order(3)
    fun getUserByCode() {
        val user = userService.createUser(User(username = "testUser10", password = "123", email = "test10@example.com"))
            ?: fail("User couldn't be created")
        mailService.markEMailAsValid(user)

        userPasswordService.generateAndSendPasswordResetCode(user)

        val passwordResetRequest = passwordResetRequestRepository.findByUser(user)

        val userByCode = passwordResetRequestRepository.findByCode(passwordResetRequest?.code ?: "")

        Assertions.assertEquals(user, userByCode?.user)
    }
}
