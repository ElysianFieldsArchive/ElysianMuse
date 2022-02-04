package org.darkSolace.muse.testUtil

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
abstract class TestBase {
    @LocalServerPort
    private lateinit var port: String

    @Autowired
    private lateinit var dbClearer: DBClearer

    init {
        postgresqlContainer.start()
    }

    fun generateUrl(path: String): String = "http://localhost:$port$path"

    @BeforeEach
    fun clearDB() {
        dbClearer.clearAll()
    }

    companion object {
        @Container
        val postgresqlContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer<Nothing>("postgres:14.0-alpine").apply {
                withDatabaseName("foo")
                withUsername("foo")
                withPassword("secret")
                withReuse(true)
            }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", postgresqlContainer::getPassword)
            registry.add("spring.datasource.username", postgresqlContainer::getUsername)
        }
    }
}