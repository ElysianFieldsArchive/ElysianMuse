package org.darkSolace.muse.testUtil

import com.icegreen.greenmail.configuration.GreenMailConfiguration
import com.icegreen.greenmail.junit5.GreenMailExtension
import com.icegreen.greenmail.util.ServerSetup
import org.darkSolace.muse.mail.service.MailerSettingsService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestBase {
    @LocalServerPort
    private lateinit var port: String

    @Autowired
    private lateinit var dbClearer: DBClearer

    @Autowired
    lateinit var mailerSettingsService: MailerSettingsService

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
            }.also {
                it.start()
            }

        @Container
        val greenMail = GenericContainer<Nothing>(DockerImageName.parse("greenmail/standalone:1.6.8")).apply {
            waitingFor(Wait.forLogMessage(".*Starting GreenMail standalone.*", 1))
            withEnv(
                "GREENMAIL_OPTS",
                "-Dgreenmail.setup.test.smtp -Dgreenmail.hostname=0.0.0.0 -Dgreenmail.users=test:testPassword"
            )
            withExposedPorts(3025)
            withReuse(true)

        }.also {
            it.start()
        }

        @RegisterExtension
        val greenMailExtension: GreenMailExtension =
            GreenMailExtension(ServerSetup(3025, "localhost", "smtp"))
                .withConfiguration(GreenMailConfiguration.aConfig().withUser("test", "testPassword"))
                .withPerMethodLifecycle(false)

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", postgresqlContainer::getPassword)
            registry.add("spring.datasource.username", postgresqlContainer::getUsername)
        }
    }
}
