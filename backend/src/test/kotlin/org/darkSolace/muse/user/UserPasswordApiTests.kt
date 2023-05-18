package org.darkSolace.muse.user

import org.darkSolace.muse.mail.repository.MailQueueRepository
import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.repository.PasswordResetRequestRepository
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class UserPasswordApiTests : TestBase() {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var mailService: MailService

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var passwordResetRequestRepository: PasswordResetRequestRepository

    @Autowired
    lateinit var mailQueueRepository: MailQueueRepository

    @Test
    @Order(1)
    fun requestResetByUsername() {
        //create user to reset password
        val user = userService.createUser(User(username = "test", password = "123456", email = "test@example.com"))
            ?: fail("User couldn't be created")
        mailService.markEMailAsValid(user)

        //request password reset
        val url = generateUrl("/api/user/reset")

        val confirmation = restTemplate.exchange(url, HttpMethod.POST, HttpEntity("test"), ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.OK, confirmation.statusCode)
        Assertions.assertNotNull(passwordResetRequestRepository.findByUser(user))
        Assertions.assertEquals(2, mailQueueRepository.count())
    }

    @Test
    @Order(2)
    fun requestResetByEMail() {
        //create user to reset password
        val user = userService.createUser(User(username = "test", password = "123456", email = "test@example.com"))
            ?: fail("User couldn't be created")
        mailService.markEMailAsValid(user)

        //request password reset
        val url = generateUrl("/api/user/reset")

        val confirmation =
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity("test@example.com"), ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.OK, confirmation.statusCode)
        Assertions.assertNotNull(passwordResetRequestRepository.findByUser(user))
        Assertions.assertEquals(2, mailQueueRepository.count())
    }

    @Test
    @Order(3)
    fun requestResetInvalidIdentifier() {
        //request password reset
        val url = generateUrl("/api/user/reset")

        val confirmation =
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity("Shrek"), ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, confirmation.statusCode)
        Assertions.assertEquals(0, mailQueueRepository.count())
    }

    @Test
    @Order(4)
    fun confirmReset() {
        //create user to reset password
        val user = userService.createUser(User(username = "test", password = "123456", email = "test@example.com"))
            ?: fail("User couldn't be created")
        mailService.markEMailAsValid(user)

        //request password reset
        val url = generateUrl("/api/user/reset")

        val confirmation = restTemplate.exchange(url, HttpMethod.POST, HttpEntity("test"), ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.OK, confirmation.statusCode)
        Assertions.assertNotNull(passwordResetRequestRepository.findByUser(user))
        Assertions.assertEquals(2, mailQueueRepository.count())

        val code = passwordResetRequestRepository.findByUser(user)?.code

        //send new password with confirmation code
        val url2 = generateUrl("/api/user/reset/$code")
        val response =
            restTemplate.exchange(url2, HttpMethod.POST, HttpEntity("newPassword"), ResponseEntity::class.java)

        val updatedUser = userService.getById(user.id ?: -1)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotEquals(user.password, updatedUser?.password)
        Assertions.assertNotEquals(user.salt, updatedUser?.salt)
    }

    @Test
    @Order(5)
    fun confirmResetInvalidCode() {
//create user to reset password
        val user = userService.createUser(User(username = "test", password = "123456", email = "test@example.com"))
            ?: fail("User couldn't be created")
        mailService.markEMailAsValid(user)

        //request password reset
        val url = generateUrl("/api/user/reset")

        val confirmation = restTemplate.exchange(url, HttpMethod.POST, HttpEntity("test"), ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.OK, confirmation.statusCode)
        Assertions.assertNotNull(passwordResetRequestRepository.findByUser(user))
        Assertions.assertEquals(2, mailQueueRepository.count())

        //send new password with invalid confirmation code
        val url2 = generateUrl("/api/user/reset/invalidCode")
        val response =
            restTemplate.exchange(url2, HttpMethod.POST, HttpEntity("newPassword"), ResponseEntity::class.java)

        val updatedUser = userService.getById(user.id ?: -1)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        //password is not changed
        Assertions.assertEquals(user.password, updatedUser?.password)
        Assertions.assertEquals(user.salt, updatedUser?.salt)
    }
}
