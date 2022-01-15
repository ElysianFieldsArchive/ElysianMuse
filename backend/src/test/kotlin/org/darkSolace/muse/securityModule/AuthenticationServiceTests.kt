package org.darkSolace.muse.securityModule

import org.darkSolace.muse.DBClearer
import org.darkSolace.muse.securityModule.model.LoginRequest
import org.darkSolace.muse.securityModule.model.SignUpRequest
import org.darkSolace.muse.securityModule.model.SignUpResponse
import org.darkSolace.muse.securityModule.service.AuthenticationService
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.service.UserRoleService
import org.darkSolace.muse.userModule.service.UserService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AuthenticationServiceTests {
    @Autowired
    lateinit var authService: AuthenticationService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRoleService: UserRoleService

    @Autowired
    lateinit var dbClearer: DBClearer

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

    @Test
    @Order(1)
    fun testSignUp() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        Assertions.assertEquals(SignUpResponse.OK, response)
    }

    @Test
    @Order(2)
    fun testSignUp_DuplicateUsername() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        Assertions.assertEquals(SignUpResponse.OK, response)

        val secondSignupRequest = SignUpRequest("test", "123", "test2@example.com")
        val secondResponse = authService.signUpUser(secondSignupRequest)
        Assertions.assertEquals(SignUpResponse.USERNAME_EXISTS, secondResponse)
    }

    @Test
    @Order(3)
    fun testSignUp_DuplicateEMail() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        Assertions.assertEquals(SignUpResponse.OK, response)

        val secondSignupRequest = SignUpRequest("test2", "123", "test@example.com")
        val secondResponse = authService.signUpUser(secondSignupRequest)
        Assertions.assertEquals(SignUpResponse.EMAIL_EXISTS, secondResponse)
    }

    @Test
    @Order(4)
    fun testSignIn() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        Assertions.assertEquals(SignUpResponse.OK, response)

        val loginRequest = LoginRequest("test", "123")
        val jwtToken = authService.authenticate(loginRequest)
        Assertions.assertNotNull(jwtToken)
    }

    @Test
    @Order(5)
    fun testSignIn_WrongPassword() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        Assertions.assertEquals(SignUpResponse.OK, response)

        val loginRequest = LoginRequest("test", "1234")
        val jwtToken = authService.authenticate(loginRequest)
        Assertions.assertNull(jwtToken)
    }

    @Test
    @Order(6)
    fun testSignIn_SuspendedUser() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        Assertions.assertEquals(SignUpResponse.OK, response)
        userRoleService.suspendUser(User(username = "test", password = "", email = "test@example.com"))

        val jwtResponse = authService.authenticate(LoginRequest("test", "123"))
        Assertions.assertEquals("SUSPENDED", jwtResponse?.role)
    }

    @Test
    @Order(7)
    fun testSignIn_UpdateLoginDate() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        Assertions.assertEquals(SignUpResponse.OK, response)

        var user = userService.userRepository.findByUsername("test")
        Assertions.assertNull(user?.lastLogInDate)

        val loginRequest = LoginRequest("test", "123")
        val jwtToken = authService.authenticate(loginRequest)
        Assertions.assertNotNull(jwtToken)

        user = userService.userRepository.findByUsername("test")
        Assertions.assertNotNull(user?.lastLogInDate)
    }
}
