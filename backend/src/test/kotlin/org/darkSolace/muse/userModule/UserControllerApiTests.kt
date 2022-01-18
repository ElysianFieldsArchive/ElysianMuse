package org.darkSolace.muse.userModule

import org.darkSolace.muse.DBClearer
import org.darkSolace.muse.securityModule.model.JwtResponse
import org.darkSolace.muse.securityModule.model.SignUpRequest
import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.service.UserRoleService
import org.darkSolace.muse.userModule.service.UserService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserControllerApiTests {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRoleService: UserRoleService

    @LocalServerPort
    private lateinit var port: String

    @Autowired
    private lateinit var dbClearer: DBClearer

    companion object {
        @Container
        private val postgresqlContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer<Nothing>("postgres:14.0-alpine").apply {
                withDatabaseName("foo")
                withUsername("foo")
                withPassword("secret")
            }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", postgresqlContainer::getPassword)
            registry.add("spring.datasource.username", postgresqlContainer::getUsername)
        }
    }

    @BeforeEach
    fun clearDB() {
        dbClearer.clearAll()
    }

    private fun generateUrl(path: String): String = "http://localhost:$port$path"


    @Test
    @Order(1)
    fun getUserById_publicUser() {
        val user = userService.createUser(User(username = "test", password = "123", email = "test@example.com"))

        val url = generateUrl("/api/user/${user?.id}")
        val response = restTemplate.getForEntity(url, User::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(user?.toPublicUser(), response.body)
    }

    @Test
    @Order(2)
    fun getUserById_ownUser() {
        var url = generateUrl("/api/auth/signup")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("User created successfully.", response.body)

        url = generateUrl("/api/auth/signin")
        val jwtResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${jwtResponse?.body?.token}")
        }

        url = generateUrl("/api/user/${jwtResponse?.body?.id}")
        val userResponse =
            restTemplate.exchange(url, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), User::class.java)

        val user = userService.getById(jwtResponse?.body?.id ?: -1)
        Assertions.assertEquals(HttpStatus.OK, userResponse.statusCode)
        Assertions.assertEquals(user, userResponse.body)
    }

    @Test
    @Order(3)
    fun getUserById_unknownId() {
        val url = generateUrl("/api/user/99")
        val response = restTemplate.getForEntity(url, User::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNull(response.body)
    }

    @Test
    @Order(4)
    fun getAll() {
        val user = userService.createUser(User(username = "test", password = "123", email = "test@example.com"))
        val user2 = userService.createUser(User(username = "test2", password = "123", email = "test2@example.com"))
        val user3 = userService.createUser(User(username = "test3", password = "123", email = "test3@example.com"))
        val user4 = userService.createUser(User(username = "test4", password = "123", email = "test4@example.com"))

        val url = generateUrl("/api/user/all")
        val response = restTemplate.getForEntity(url, Array<User>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertTrue(response.body?.contains(user?.toPublicUser()) ?: false)
        Assertions.assertTrue(response.body?.contains(user2?.toPublicUser()) ?: false)
        Assertions.assertTrue(response.body?.contains(user3?.toPublicUser()) ?: false)
        Assertions.assertTrue(response.body?.contains(user4?.toPublicUser()) ?: false)
    }

    @Test
    @Order(5)
    fun getAll_Empty() {
        val url = generateUrl("/api/user/all")
        val response = restTemplate.getForEntity(url, Array<User>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size ?: 99)
    }

    @Test
    @Order(6)
    fun suspendUser_success() {
        //generate user to suspend
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate admin to perform the suspension
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //suspend user via api
        url = generateUrl("/api/user/suspend/${user?.id}")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity<HttpHeaders>(headers), HttpStatus::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertEquals(user?.role, Role.SUSPENDED)
    }

    @Test
    @Order(7)
    fun suspendUser_noRights() {
        //generate user to suspend
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate second user to perform the suspension
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to suspend user via api
        url = generateUrl("/api/user/suspend/${user?.id}")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity<HttpHeaders>(headers), HttpStatus::class.java)

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertEquals(user?.role, Role.MEMBER)
    }

    @Test
    @Order(8)
    fun suspendUser_invalidId() {
        //generate admin to perform the suspension
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to suspend user via api
        url = generateUrl("/api/user/suspend/4711")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity<HttpHeaders>(headers), ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(9)
    fun confirmSuspension_success() {
        //generate user to suspend
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate admin to perform the suspension
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //suspend user via api
        url = generateUrl("/api/user/suspend/${user?.id}")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity<HttpHeaders>(headers), ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertEquals(user?.role, Role.SUSPENDED)

        //try to sign in suspended user
        url = generateUrl("/api/auth/signin")
        val userSignInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "1234", "test@example.com"),
            String::class.java
        )

        Assertions.assertEquals(HttpStatus.TEMPORARY_REDIRECT, userSignInResponse.statusCode)
        val confirmationUrl = generateUrl("/api/user/${userSignInResponse.headers.location?.toString()}")

        val confirmation = restTemplate.exchange(confirmationUrl, HttpMethod.POST, null, ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.OK, confirmation.statusCode)
        user = userService.getById(user?.id ?: -1)
        Assertions.assertEquals(user?.role, Role.MEMBER)
    }

    @Test
    @Order(10)
    fun confirmSuspension_unknownCode() {
        val confirmationUrl = generateUrl("/api/user/suspend/confirm/testConfirmationCode")

        val confirmation = restTemplate.exchange(confirmationUrl, HttpMethod.POST, null, ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, confirmation.statusCode)
    }
}
