package org.darkSolace.muse.securityModule

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.darkSolace.muse.DBClearer
import org.darkSolace.muse.securityModule.model.SignUpRequest
import org.darkSolace.muse.securityModule.service.AuthenticationService
import org.darkSolace.muse.securityModule.service.JwtUtils
import org.junit.jupiter.api.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*


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

    @Value("\${muse.app.jwtSecret}")
    private val jwtSecret: String? = null

    @Value("\${muse.app.jwtExpirationMs}")
    private val jwtExpirationMs = 0

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
        authService.signUpUser(SignUpRequest("test", "123", "test@example.com"))
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken("test", "123")
        )
        val token = jwtUtils.generateJwtToken(authentication)
        //content of token can't be checked because of changing issuing and expiration date
        Assertions.assertNotNull(token)
    }

    @Test
    fun getUserNameFromJwtToken() {
        authService.signUpUser(SignUpRequest("test", "123", "test@example.com"))
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken("test", "123")
        )
        val token = jwtUtils.generateJwtToken(authentication)
        Assertions.assertNotNull(token)
        Assertions.assertEquals("test", jwtUtils.getUserNameFromJwtToken(token))
    }

    @Test
    fun validateJwtToken() {
        authService.signUpUser(SignUpRequest("test", "123", "test@example.com"))
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken("test", "123")
        )
        val token = jwtUtils.generateJwtToken(authentication)
        Assertions.assertNotNull(token)
        Assertions.assertTrue(jwtUtils.validateJwtToken(token))
    }


    @Test
    fun validateJwtToken_invalid() {
        authService.signUpUser(SignUpRequest("test", "123", "test@example.com"))
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken("test", "123")
        )
        val token = jwtUtils.generateJwtToken(authentication)
        Assertions.assertNotNull(token)

        //make token invalid by replacing the last 8 characters
        val invalidToken = "${token?.take(token.length - 8)}12345678"
        Assertions.assertFalse(jwtUtils.validateJwtToken(invalidToken))
    }

    @Test
    fun invalidToken() {
        //Verify error message in Logs, prepare logger
        val fooLogger: Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        fooLogger.addAppender(listAppender)

        //create valid token
        val token =
            Jwts.builder().setSubject("test").setIssuedAt(Date()).setExpiration(Date(Date().time + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret?.toByteArray()), SignatureAlgorithm.HS512).compact()

        //make it invalid
        val invalidToken = "${token?.take(token.length - 8)}12345678"

        //SecurityException should be caught by validateJwtToken
        jwtUtils.validateJwtToken(invalidToken)

        // JUnit assertions
        val lastLog = listAppender.list.last().message
        Assertions.assertTrue(lastLog.contains("Invalid JWT signature"))
    }

    @Test
    fun malformedToken() {
        //Verify error message in Logs, prepare logger
        val fooLogger: Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        fooLogger.addAppender(listAppender)

        //create valid token
        val token =
            Jwts.builder()
                .setSubject("test")
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret?.toByteArray()), SignatureAlgorithm.HS512)
                .compact()

        //make it malformed by replacing the separating "." with "_"
        val malformedToken = token.replace(".", "_")

        //MalformedJwtException should be caught by validateJwtToken
        jwtUtils.validateJwtToken(malformedToken)

        // JUnit assertions
        val lastLog = listAppender.list.last().message
        Assertions.assertTrue(lastLog.contains("Invalid JWT token"))
    }

    @Test
    fun expiredToken() {
        //Verify error message in Logs, prepare logger
        val fooLogger: Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        fooLogger.addAppender(listAppender)

        //create expired token
        val token =
            Jwts.builder()
                .setSubject("test")
                .setIssuedAt(Date(Date().time - jwtExpirationMs * 2))
                .setExpiration(Date(Date().time - jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret?.toByteArray()), SignatureAlgorithm.HS512)
                .compact()

        //ExpiredJwtException should be caught by validateJwtToken
        jwtUtils.validateJwtToken(token)

        // JUnit assertions
        val lastLog = listAppender.list.last().message
        Assertions.assertTrue(lastLog.contains("JWT token is expired"))
    }

    @Test
    fun unsupportedToken() {
        //Verify error message in Logs, prepare logger
        val fooLogger: Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        fooLogger.addAppender(listAppender)

        //create unsupported, because unsigned, token
        val token =
            Jwts.builder()
                .setSubject("test")
                .setIssuedAt(Date(Date().time))
                .setExpiration(Date(Date().time + jwtExpirationMs))
                .compact()

        //UnsupportedJwtException should be caught by validateJwtToken
        jwtUtils.validateJwtToken(token)

        // JUnit assertions
        val lastLog = listAppender.list.last().message
        Assertions.assertTrue(lastLog.contains("JWT token is unsupported"))
    }

    @Test
    fun emptyToken() {
        //Verify error message in Logs, prepare logger
        val fooLogger: Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        fooLogger.addAppender(listAppender)

        //IllegalArgumentException should be caught by validateJwtToken
        jwtUtils.validateJwtToken("")

        // JUnit assertions
        val lastLog = listAppender.list.last().message
        Assertions.assertTrue(lastLog.contains("JWT claims string is empty"))
    }
}
