package org.darkSolace.muse.lastSeen

import org.darkSolace.muse.lastSeen.service.LastSeenService
import org.darkSolace.muse.security.model.LoginRequest
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.security.service.AuthenticationService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.service.UserService
import org.hibernate.StaleStateException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod


/**
 * session.time is set to 1 minute for those test
 */
class LastSeenApiTests : TestBase() {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var authService: AuthenticationService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var lastSeenService: LastSeenService

    @Test
    @Order(1)
    fun generateLastSeenFromJSession() {
        val url = generateUrl("/api/lastSeen/online/count")
        val response = restTemplate.getForEntity(url, Int::class.java)

        Assertions.assertEquals(1, response?.body)
    }

    @Test
    @Order(2)
    fun generateLastSeenFromUser() {
        //generate user
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        authService.signUpUser(signupRequest)
        userService.markEMailAsValid("test")
        val loginRequest = LoginRequest("test", "123")
        val jwtResponse = authService.authenticate(loginRequest)


        //access api anonymously to create jSession last seen entry
        var url = generateUrl("/api/lastSeen/online/count")
        var response = restTemplate.getForEntity(url, Int::class.java)
        val jSessionId = response.headers["Set-Cookie"]
            ?.first { it.startsWith("JSESSIONID") }

        Assertions.assertEquals(1, response?.body)

        //access Api with user-token to be identifiable, previous last seen entry should get a username added
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${jwtResponse?.token}")
            add("Cookie", jSessionId)
        }
        response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), Int::class.java)
        //there should be still only one entry
        Assertions.assertEquals(1, response?.body)

        url = generateUrl("/api/lastSeen/online")
        val response2 =
            restTemplate.exchange(url, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), Array<User>::class.java)

        //check if user is correctly retrieved
        Assertions.assertEquals(1, response2.body?.size)
        Assertions.assertEquals(1, response2.body?.count { it.username == "test" })
    }

    @Test
    @Order(3)
    fun timeoutLastSeen() {
        val url = generateUrl("/api/lastSeen/online/count")
        var response = restTemplate.getForEntity(url, Int::class.java)

        Assertions.assertEquals(1, response?.body)

        //wait a minute (+ a bit), run prune manually to ensure it was executed after the timeout
        Thread.sleep((1 * 60 * 1000) + 50)
        try {
            lastSeenService.prune()
        } catch (_: StaleStateException) {
            // it is possible, but unlikely, that the scheduler executes prune while we do it manually
            // Catching the resulting exception just to be sure
        }

        //call the api again (this creates a new session, as no information from the previous session was supplied)
        response = restTemplate.getForEntity(url, Int::class.java)

        //if the old session was deleted it should still be "one" again
        Assertions.assertEquals(1, response?.body)
    }

    @Test
    @Order(4)
    fun refreshLastSeenFromJSession() {
        val url = generateUrl("/api/lastSeen/online/count")
        val response = restTemplate.getForEntity(url, Int::class.java)
        Assertions.assertEquals(1, response?.body)

        val jSessionId = response.headers["Set-Cookie"]
            ?.first { it.startsWith("JSESSIONID") }
        val lastSeenEntry = lastSeenService.getBySession(jSessionId?.split(";")?.first()?.split("=")?.last())

        //sleep some time (1 second)
        Thread.sleep(1000)

        //call api again with session cookie set
        val headers = HttpHeaders().apply {
            add("Cookie", jSessionId)
        }
        restTemplate.exchange(url, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), Int::class.java)

        val newSeenEntry = lastSeenService.getBySession(jSessionId?.split(";")?.first()?.split("=")?.last())
        Assertions.assertNotEquals(newSeenEntry?.lastSeen, lastSeenEntry?.lastSeen)
    }

    @Test
    @Order(5)
    fun refreshLastSeenFromUser() {
        //generate user
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        authService.signUpUser(signupRequest)
        userService.markEMailAsValid("test")

        val loginRequest = LoginRequest("test", "123")
        val jwtResponse = authService.authenticate(loginRequest)

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${jwtResponse?.token}")
        }

        //access api anonymously to create jSession last seen entry
        val url = generateUrl("/api/lastSeen/online/count")
        val response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), Int::class.java)
        val jSessionId = response.headers["Set-Cookie"]
            ?.first { it.startsWith("JSESSIONID") }
        headers.add("Cookie", jSessionId)
        val lastSeenEntry = lastSeenService.getByUsername("test")

        //sleep some time (1 second)
        Thread.sleep(1000)

        //access api again to refresh lastSeenTimer
        restTemplate.exchange(url, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), Int::class.java)

        val newSeenEntry = lastSeenService.getByUsername("test")
        Assertions.assertNotEquals(newSeenEntry?.lastSeen, lastSeenEntry?.lastSeen)
    }

    @Test
    @Order(6)
    fun generateMultipleLastSeenEntries() {
        val url = generateUrl("/api/lastSeen/online/count")
        val response = restTemplate.getForEntity(url, Int::class.java)
        val response2 = restTemplate.getForEntity(url, Int::class.java)

        Assertions.assertEquals(1, response?.body)
        Assertions.assertEquals(2, response2?.body)
    }
}
