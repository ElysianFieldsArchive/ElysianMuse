package org.darkSolace.muse.userModule

import org.darkSolace.muse.DBClearer
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.service.UserService
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
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserControllerApiTests {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var userService: UserService

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
    fun getUserById() {
        val user = userService.createUser(User(username = "test", password = "123", email = "test@example.com"))

        val url = generateUrl("/api/user/${user.id}")
        val response = restTemplate.getForEntity(url, User::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(user, response.body)
    }

    @Test
    @Order(2)
    fun getUserById_unknownId() {
        val url = generateUrl("/api/user/99")
        val response = restTemplate.getForEntity(url, User::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNull(response.body)
    }

    @Test
    @Order(3)
    fun getAll() {
        val user = userService.createUser(User(username = "test", password = "123", email = "test@example.com"))
        val user2 = userService.createUser(User(username = "test2", password = "123", email = "test2@example.com"))
        val user3 = userService.createUser(User(username = "test3", password = "123", email = "test3@example.com"))
        val user4 = userService.createUser(User(username = "test4", password = "123", email = "test4@example.com"))

        val url = generateUrl("/api/user/all")
        val response = restTemplate.getForEntity(url, Array<User>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertTrue(response.body?.contains(user) ?: false)
        Assertions.assertTrue(response.body?.contains(user2) ?: false)
        Assertions.assertTrue(response.body?.contains(user3) ?: false)
        Assertions.assertTrue(response.body?.contains(user4) ?: false)
    }

    @Test
    @Order(4)
    fun getAll_Empty() {
        val url = generateUrl("/api/user/all")
        val response = restTemplate.getForEntity(url, Array<User>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size ?: 99)
    }
}
