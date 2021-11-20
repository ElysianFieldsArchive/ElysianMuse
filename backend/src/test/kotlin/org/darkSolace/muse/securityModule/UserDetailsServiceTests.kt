package org.darkSolace.muse.securityModule

import org.darkSolace.muse.DBClearer
import org.darkSolace.muse.securityModule.model.UserDetails
import org.darkSolace.muse.securityModule.service.UserDetailsService
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class UserDetailsServiceTests {
    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var userService: UserService

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
    fun loadUserDetailsByUsername() {
        userService.createUser(User(username = "testUser", password = "123", email = "test@example.com"))

        val userDetails = userDetailsService.loadUserByUsername("testUser")
        Assertions.assertInstanceOf(UserDetails::class.java, userDetails)
        Assertions.assertEquals(userDetails.username, "testUser")
    }

    @Test
    fun loadUserDetailsByUsername_unknownUser() {
        val e = Assertions.assertThrows(IllegalArgumentException::class.java) {
            userDetailsService.loadUserByUsername("testUser")
        }

        Assertions.assertEquals("Invalid username", e.message)
    }

    @Test
    fun loadUserDetailsByUsername_emptyUser() {
        val e = Assertions.assertThrows(IllegalArgumentException::class.java) {
            userDetailsService.loadUserByUsername("")
        }

        Assertions.assertEquals("Empty username", e.message)
    }
}
