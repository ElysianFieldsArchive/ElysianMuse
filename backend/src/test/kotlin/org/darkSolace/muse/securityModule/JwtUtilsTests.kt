package org.darkSolace.muse.securityModule

import org.darkSolace.muse.DBClearer
import org.darkSolace.muse.securityModule.model.SignupRequest
import org.darkSolace.muse.securityModule.service.AuthenticationService
import org.darkSolace.muse.securityModule.service.JwtUtils
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class JwtUtilsTests {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var authService: AuthenticationService

    @Autowired
    lateinit var jwtUtils: JwtUtils

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
    fun generateJwtToken() {
        authService.signUpUser(SignupRequest("test", "123", "test@example.com"))
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken("test", "123")
        )
        val token = jwtUtils.generateJwtToken(authentication)
        //content of token can't be checked because of changing issuing and expiration date
        Assertions.assertNotNull(token)
    }

    @Test
    fun getUserNameFromJwtToken() {
        authService.signUpUser(SignupRequest("test", "123", "test@example.com"))
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken("test", "123")
        )
        val token = jwtUtils.generateJwtToken(authentication)
        Assertions.assertNotNull(token)
        Assertions.assertEquals("test", jwtUtils.getUserNameFromJwtToken(token))
    }

    @Test
    fun validateJwtToken() {
        authService.signUpUser(SignupRequest("test", "123", "test@example.com"))
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken("test", "123")
        )
        val token = jwtUtils.generateJwtToken(authentication)
        Assertions.assertNotNull(token)
        Assertions.assertTrue(jwtUtils.validateJwtToken(token))
    }


    @Test
    fun validateJwtToken_invalid() {
        authService.signUpUser(SignupRequest("test", "123", "test@example.com"))
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken("test", "123")
        )
        val token = jwtUtils.generateJwtToken(authentication)
        Assertions.assertNotNull(token)

        //make token invalid by replacing the last 8 characters
        val invalidToken = "${token?.take(token.length - 8)}12345678"
        Assertions.assertFalse(jwtUtils.validateJwtToken(invalidToken))
    }
}
