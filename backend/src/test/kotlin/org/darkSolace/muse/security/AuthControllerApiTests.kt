package org.darkSolace.muse.security

import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.service.UserRoleService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

class AuthControllerApiTests : TestBase() {
    @Autowired
    private lateinit var userRoleService: UserRoleService

    @Autowired
    private lateinit var mailService: MailService

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    @Order(1)
    fun testSignUp() {
        val url = generateUrl("/api/auth/signup")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("User created successfully.", response.body)
    }

    @Test
    @Order(2)
    fun testSignUp_DuplicateUsername() {
        val url = generateUrl("/api/auth/signup")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("User created successfully.", response.body)

        val secondResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test2@example.com"),
            String::class.java
        )

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, secondResponse.statusCode)
        Assertions.assertEquals("Error: Username is already in use!", secondResponse.body)
    }

    @Test
    @Order(3)
    fun testSignUp_DuplicateEMail() {
        val url = generateUrl("/api/auth/signup")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("User created successfully.", response.body)

        val secondResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, secondResponse.statusCode)
        Assertions.assertEquals("Error: Email is already in use!", secondResponse.body)
    }

    @Test
    @Order(4)
    fun testSignIn() {
        val url = generateUrl("/api/auth/signup")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("User created successfully.", response.body)
        mailService.markEMailAsValid("test")

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )
        Assertions.assertEquals(HttpStatus.OK, secondResponse.statusCode)
        Assertions.assertNotNull(secondResponse.body)
        Assertions.assertInstanceOf(JwtResponse::class.java, secondResponse.body)
        Assertions.assertEquals("test", secondResponse.body?.username)
    }

    @Test
    @Order(5)
    fun testSignIn_WrongPassword() {
        val url = generateUrl("/api/auth/signup")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("User created successfully.", response.body)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "1234", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, secondResponse.statusCode)
        Assertions.assertEquals("Unknown username or wrong password!", secondResponse.body)

    }

    @Test
    @Order(6)
    fun testSignIn_SuspendedUser() {
        val url = generateUrl("/api/auth/signup")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("User created successfully.", response.body)

        userRoleService.suspendUser(User(username = "test", password = "", email = ""))

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.TEMPORARY_REDIRECT, secondResponse.statusCode)
        Assertions.assertNotNull(secondResponse.headers["Location"])
        Assertions.assertEquals("User is suspended", secondResponse.body)
    }

    @Test
    @Order(7)
    fun testSignIn_UnknownUser() {
        // no user created, trying to sign in with unknown user
        val url = generateUrl("/api/auth/signin")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        Assertions.assertEquals("Unknown username or wrong password!", response.body)
    }

    @Test
    @Order(8)
    fun testSignIn_UnknownUserEmptyPassword() {
        // no user created, trying to sign in with unknown user
        val url = generateUrl("/api/auth/signin")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        Assertions.assertEquals("Unknown username or wrong password!", response.body)
    }

    @Test
    @Order(9)
    fun testSignIn_UnconfirmedEmail() {
        val url = generateUrl("/api/auth/signup")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("User created successfully.", response.body)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, secondResponse.statusCode)
        Assertions.assertEquals("Email is not validated!", secondResponse.body)
    }
}
