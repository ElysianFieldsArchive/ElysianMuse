package org.darkSolace.muse.securityModule

import org.darkSolace.muse.DBClearer
import org.darkSolace.muse.securityModule.model.JwtResponse
import org.darkSolace.muse.securityModule.model.SignUpRequest
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.service.UserRoleService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AuthControllerApiTests {
    @Autowired
    private lateinit var userRoleService: UserRoleService

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

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
}
