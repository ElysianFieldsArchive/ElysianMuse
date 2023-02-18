package org.darkSolace.muse.mail

import org.darkSolace.muse.mail.model.MailerSettings
import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.service.UserRoleService
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

internal class MailApiTests : TestBase() {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mailService: MailService

    @Autowired
    private lateinit var userRoleService: UserRoleService

    @AfterEach
    fun cleanUp() {
        greenMailExtension.purgeEmailFromAllMailboxes()
    }

    @Test
    fun processEMailConfirmation() {
        var user =
            userService.createUser(
                User(null, "test", "", email = "test@example.org")
            ) ?: fail("Couldn't create user")
        val code = user.emailConfirmationCode

        Assertions.assertFalse(user.emailConfirmed)

        val url = generateUrl("/api/mail/confirm/$code")
        val response = restTemplate.getForEntity(url, Unit::class.java)

        //update user
        user = userService.getById(user.id ?: -1) ?: fail("User not found")

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("", user.emailConfirmationCode)
        Assertions.assertTrue(user.emailConfirmed)
    }

    @Test
    fun processEMailConfirmation_unknownCode() {
        val url = generateUrl("/api/mail/confirm/1111-111-111-1")
        val response = restTemplate.getForEntity(url, Unit::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun updateMailerSettings() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        //try accessing restricted to role MEMBER method
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }
        val newSettings = MailerSettings(null).apply {
            host = "test.com"
            port = 666
            username = "newUser"
            password = "newPass"
            fromAddress = "new@example.com"
        }
        val oldSettings = mailerSettingsService.getCurrentSettings()
        val url3 = generateUrl("/api/mail/settings")

        val response = restTemplate.exchange(url3, HttpMethod.POST, HttpEntity(newSettings, headers), Unit::class.java)

        //clear id, as we don't know it, and it doesn't matter for this test
        val maybeNewSettings = mailerSettingsService.getCurrentSettings().apply { id = null }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotEquals(maybeNewSettings.toString(), oldSettings.toString())
        Assertions.assertEquals(maybeNewSettings.toString(), newSettings.toString())
    }

    @Test
    fun updateMailerSettings_invalid() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")
        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.ADMINISTRATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        //try accessing restricted to role MEMBER method
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }
        val url3 = generateUrl("/api/mail/settings")

        //trying to send something other than mail settings
        val response =
            restTemplate.exchange(url3, HttpMethod.POST, HttpEntity(secondResponse, headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }
}
