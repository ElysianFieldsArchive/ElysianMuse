package org.dark_solace.muse

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.persistence.EntityManager


@SpringBootTest
@Testcontainers
class ElysianMuseApplicationTests {

    @Autowired
    lateinit var em: EntityManager

    companion object {
        @Container
        private val postgresqlContainer: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>("postgres:14.0").apply {
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

    /**
     * Checks if Spring Boot is starting without issues
     */
    @Test
    fun contextLoads() {
    }

    /**
     * Checks if the testcontainer is loading properly without issues
     */
    @Test
    fun testContainerLoads() {
        val result = em.createNativeQuery("SELECT 1 FROM pg_tables").singleResult
        Assertions.assertTrue(result is Int)
        Assertions.assertEquals(1, (result as Int))
    }
}
