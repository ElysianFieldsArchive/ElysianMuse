package org.darkSolace.muse.userModule

import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.service.UserRoleService
import org.darkSolace.muse.userModule.service.UserService
import org.junit.jupiter.api.Assertions
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
class UserRoleServiceTests {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRoleService: UserRoleService

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

    @Test
    fun suspendUser() {
        val user = userService.createUser(User(username = "testUser3", password = "123", email = "test3@example.com"))
        Assertions.assertNotEquals(Role.SUSPENDED, user.role)
        userRoleService.suspendUser(user)
        Assertions.assertEquals(Role.SUSPENDED, user.role)
    }

    @Test
    fun changeRole() {
        val user = userService.createUser(User(username = "testUser4", password = "123", email = "test4@example.com"))
        Assertions.assertEquals(Role.MEMBER, user.role)
        userRoleService.changeRole(user, Role.MODERATOR)
        Assertions.assertEquals(Role.MODERATOR, user.role)
        userRoleService.changeRole(user, Role.ADMINISTRATOR)
        Assertions.assertEquals(Role.ADMINISTRATOR, user.role)
        userRoleService.changeRole(user, Role.SUSPENDED)
        Assertions.assertEquals(Role.SUSPENDED, user.role)
    }

    @Test
    fun changeRoleByUsername() {
        var user = userService.createUser(User(username = "testUser94", password = "123", email = "test94@example.com"))
        Assertions.assertEquals(Role.MEMBER, user.role)
        val userByUserName = User(username = "testUser94", password = "", email = "")
        user = userRoleService.changeRole(userByUserName, Role.MODERATOR)
        Assertions.assertEquals(Role.MODERATOR, user.role)
        user = userRoleService.changeRole(userByUserName, Role.ADMINISTRATOR)
        Assertions.assertEquals(Role.ADMINISTRATOR, user.role)
        user = userRoleService.changeRole(userByUserName, Role.SUSPENDED)
        Assertions.assertEquals(Role.SUSPENDED, user.role)
    }
}
