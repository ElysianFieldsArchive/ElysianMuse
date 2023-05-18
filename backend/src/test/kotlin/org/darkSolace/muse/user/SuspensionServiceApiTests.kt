package org.darkSolace.muse.user

import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.SuspensionHistoryEntry
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.service.UserRoleService
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

class SuspensionServiceApiTests : TestBase() {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mailService: MailService

    @Autowired
    private lateinit var userRoleService: UserRoleService

    @Test
    @Order(1)
    fun getSuspensionHistoryUnauthorized() {
        val user = userService.createUser(User(username = "test", password = "123456", email = "test@example.com"))

        userRoleService.suspendUser(user?.id ?: -1)

        val url = generateUrl("/api/user/suspend/history/${user?.id}")
        val response = restTemplate.getForEntity(url, Unit::class.java)
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response?.statusCode)
    }

    @Test
    @Order(2)
    fun getSuspensionHistoryModerator() {
        val user = userService.createUser(User(username = "test", password = "123456", email = "test@example.com"))
        val moderator =
            userService.createUser(User(username = "test2", password = "123456", email = "test2@example.com"))
                ?: fail("Couldn't create moderator user")
        mailService.markEMailAsValid(moderator)
        userRoleService.changeRole(moderator, Role.MODERATOR)
        userRoleService.suspendUser(user?.id ?: -1)

        //login mod to obtain token
        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl,
            SignUpRequest("test2", "123456", "test2@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        //get SuspensionHistory as Moderator
        val url = generateUrl("/api/user/suspend/history/${user?.id}")
        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Array<SuspensionHistoryEntry>::class.java
        )
        Assertions.assertEquals(HttpStatus.OK, response?.statusCode)
        val suspensionHistory = response?.body
        Assertions.assertTrue(suspensionHistory?.isNotEmpty() ?: false)
        Assertions.assertEquals(1, suspensionHistory?.count { it.user == user })
    }

    @Test
    @Order(3)
    fun getSuspensionHistoryAdmin() {
        val user = userService.createUser(User(username = "test", password = "123456", email = "test@example.com"))
        val admin = userService.createUser(User(username = "test2", password = "123456", email = "test2@example.com"))
            ?: fail("Couldn't create admin user")
        mailService.markEMailAsValid(admin)
        userRoleService.changeRole(admin, Role.ADMINISTRATOR)
        userRoleService.suspendUser(user?.id ?: -1)

        //login admin to obtain token
        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl,
            SignUpRequest("test2", "123456", "test2@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        //get SuspensionHistory as Administrator
        val url = generateUrl("/api/user/suspend/history/${user?.id}")
        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            Array<SuspensionHistoryEntry>::class.java
        )
        Assertions.assertEquals(HttpStatus.OK, response?.statusCode)
        val suspensionHistory = response?.body
        Assertions.assertTrue(suspensionHistory?.isNotEmpty() ?: false)
        Assertions.assertEquals(1, suspensionHistory?.count { it.user == user })
    }

    @Test
    @Order(4)
    fun getAllSuspendedUnauthorized() {
        val user = userService.createUser(User(username = "test", password = "123456", email = "test@example.com"))

        userRoleService.suspendUser(user?.id ?: -1)

        val url = generateUrl("/api/user/suspend/all")
        val response = restTemplate.getForEntity(url, Unit::class.java)
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response?.statusCode)
    }

    @Test
    @Order(5)
    fun getAllSuspendedAsModerator() {
        val user = userService.createUser(User(username = "test", password = "123456", email = "test@example.com"))
        val moderator =
            userService.createUser(User(username = "test2", password = "123456", email = "test2@example.com"))
                ?: fail("Couldn't create moderator user")
        mailService.markEMailAsValid(moderator)
        userRoleService.changeRole(moderator, Role.MODERATOR)
        userRoleService.suspendUser(user?.id ?: -1)

        //login mod to obtain token
        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl,
            SignUpRequest("test2", "123456", "test2@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        //get SuspensionHistory as Moderator
        val url = generateUrl("/api/user/suspend/all")
        val response =
            restTemplate.exchange(url, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), Array<User>::class.java)
        Assertions.assertEquals(HttpStatus.OK, response?.statusCode)
        val suspendedUsers = response?.body
        Assertions.assertTrue(suspendedUsers?.isNotEmpty() ?: false)
        Assertions.assertEquals(1, suspendedUsers?.count { it == user })
    }

    @Test
    @Order(6)
    fun getAllSuspendedAsAdmin() {
        val user = userService.createUser(User(username = "test", password = "123456", email = "test@example.com"))
        val admin = userService.createUser(User(username = "test2", password = "123456", email = "test2@example.com"))
            ?: fail("Couldn't create admin user")
        mailService.markEMailAsValid(admin)
        userRoleService.changeRole(admin, Role.MODERATOR)
        userRoleService.suspendUser(user?.id ?: -1)

        //login admin to obtain token
        val signInUrl = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            signInUrl,
            SignUpRequest("test2", "123456", "test2@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }

        //get SuspensionHistory as Administrator
        val url = generateUrl("/api/user/suspend/all")
        val response =
            restTemplate.exchange(url, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), Array<User>::class.java)
        Assertions.assertEquals(HttpStatus.OK, response?.statusCode)
        val suspendedUsers = response?.body
        Assertions.assertTrue(suspendedUsers?.isNotEmpty() ?: false)
        Assertions.assertEquals(1, suspendedUsers?.count { it == user })
    }
}
