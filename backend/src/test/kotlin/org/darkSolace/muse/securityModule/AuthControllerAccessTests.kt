package org.darkSolace.muse.securityModule

import org.darkSolace.muse.securityModule.model.JwtResponse
import org.darkSolace.muse.securityModule.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.service.UserRoleService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

class AuthControllerAccessTests : TestBase() {
    @Autowired
    private lateinit var userRoleService: UserRoleService

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    @Order(1)
    fun testTestController() {
        // no user created, trying to sign in with unknown user
        val url = generateUrl("/api/test/auth/")
        val response = restTemplate.getForEntity(url, String::class.java)
        println(response)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("Test passed", response.body)
    }

    @Test
    @Order(2)
    fun testPreAuthentication_Member_Fail() {
        // try accessing restricted to 'MEMBER' method without sign in
        val url = generateUrl("/api/test/auth/restricted_member")
        val response = restTemplate.getForEntity(url, String::class.java)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    }

    @Test
    @Order(3)
    fun testPreAuthentication_Member_Pass() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
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
        val url3 = generateUrl("/api/test/auth/restricted_member")
        val response3 =
            restTemplate.exchange(url3, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), String::class.java)

        Assertions.assertEquals(HttpStatus.OK, response3.statusCode)
        Assertions.assertEquals("Test passed", response3.body)
    }

    @Test
    @Order(4)
    fun testPreAuthentication_Admin_Fail() {
        // try accessing restricted to 'ADMINISTRATOR' method without sign in
        val url = generateUrl("/api/test/auth/restricted_admin")
        val response = restTemplate.getForEntity(url, String::class.java)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    }

    @Test
    @Order(5)
    fun testPreAuthentication_Admin_Pass() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )

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
        val url3 = generateUrl("/api/test/auth/restricted_admin")
        val response3 =
            restTemplate.exchange(url3, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), String::class.java)

        Assertions.assertEquals(HttpStatus.OK, response3.statusCode)
        Assertions.assertEquals("Test passed", response3.body)
    }

    @Test
    @Order(6)
    fun testPreAuthentication_Mod_Fail() {
        // try accessing restricted to 'MODERATOR' method without sign in
        val url = generateUrl("/api/test/auth/restricted_mod")
        val response = restTemplate.getForEntity(url, String::class.java)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    }

    @Test
    @Order(7)
    fun testPreAuthentication_Mod_Pass() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MODERATOR)

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
        val url3 = generateUrl("/api/test/auth/restricted_mod")
        val response3 =
            restTemplate.exchange(url3, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), String::class.java)

        Assertions.assertEquals(HttpStatus.OK, response3.statusCode)
        Assertions.assertEquals("Test passed", response3.body)
    }

    @Test
    @Order(8)
    fun testPreAuthentication_AllButMember_Unauthorized_Fail() {
        // try accessing restricted to 'MODERATOR' or 'ADMINISTRATOR' method without sign in
        val url = generateUrl("/api/test/auth/restricted_many")
        val response = restTemplate.getForEntity(url, String::class.java)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    }

    @Test
    @Order(9)
    fun testPreAuthentication_AllButMember_AsMember_Fail() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }
        val url3 = generateUrl("/api/test/auth/restricted_many")
        val response3 =
            restTemplate.exchange(url3, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), String::class.java)
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response3.statusCode)
    }

    @Test
    @Order(10)
    fun testPreAuthentication_AllButMember_AsMod_Pass() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test", password = "", email = ""), Role.MODERATOR)

        val url2 = generateUrl("/api/auth/signin")
        val secondResponse = restTemplate.postForEntity(
            url2,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${secondResponse?.body?.token}")
        }
        val url3 = generateUrl("/api/test/auth/restricted_many")
        val response3 =
            restTemplate.exchange(url3, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), String::class.java)

        Assertions.assertEquals(HttpStatus.OK, response3.statusCode)
        Assertions.assertEquals("Test passed", response3.body)
    }

    @Test
    @Order(11)
    fun testPreAuthentication_AllButMember_AsAdmin_Pass() {
        //create user and sign in
        val url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )

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
        val url3 = generateUrl("/api/test/auth/restricted_many")
        val response3 =
            restTemplate.exchange(url3, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), String::class.java)

        Assertions.assertEquals(HttpStatus.OK, response3.statusCode)
        Assertions.assertEquals("Test passed", response3.body)
    }
}
