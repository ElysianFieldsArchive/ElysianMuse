package org.darkSolace.muse.user

import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.repository.SuspensionHistoryRepository
import org.darkSolace.muse.user.service.UserRoleService
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired

class UserRoleServiceTests : TestBase() {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRoleService: UserRoleService

    @Autowired
    lateinit var suspensionHistoryRepository: SuspensionHistoryRepository

    @Test
    fun suspendUser() {
        val user =
            userService.createUser(User(username = "testUser3", password = "123456", email = "test3@example.com"))
                ?: fail("user is null")
        Assertions.assertNotEquals(Role.SUSPENDED, user.role)
        val suspendedUser = userRoleService.suspendUser(user)
        Assertions.assertEquals(Role.SUSPENDED, suspendedUser?.role)
    }

    @Test
    fun suspendUserById() {
        val user =
            userService.createUser(User(username = "testUser3", password = "123456", email = "test3@example.com"))
                ?: fail("user is null")
        Assertions.assertNotEquals(Role.SUSPENDED, user.role)
        val suspendedUser = userRoleService.suspendUser(user.id ?: -1)
        Assertions.assertEquals(Role.SUSPENDED, suspendedUser?.role)
    }

    @Test
    fun suspendUserAlreadySuspended() {
        val user =
            userService.createUser(User(username = "testUser3", password = "123456", email = "test3@example.com"))
                ?: fail("user is null")
        Assertions.assertNotEquals(Role.SUSPENDED, user.role)
        var suspendedUser = userRoleService.suspendUser(user)
        Assertions.assertEquals(Role.SUSPENDED, suspendedUser?.role)
        suspendedUser = userRoleService.suspendUser(user)
        Assertions.assertEquals(Role.SUSPENDED, suspendedUser?.role)
    }

    @Test
    fun suspendUserAlreadySuspendedWithMissingSuspensionHistoryEntry() {
        val user =
            userService.createUser(User(username = "testUser3", password = "123456", email = "test3@example.com"))
                ?: fail("user is null")
        Assertions.assertNotEquals(Role.SUSPENDED, user.role)
        var suspendedUser = userRoleService.suspendUser(user)
        Assertions.assertEquals(Role.SUSPENDED, suspendedUser?.role)
        suspensionHistoryRepository.deleteAll()
        suspendedUser = userRoleService.suspendUser(user)
        Assertions.assertEquals(Role.SUSPENDED, suspendedUser?.role)
        Assertions.assertEquals(1, suspensionHistoryRepository.count())
    }

    @Test
    fun changeRole() {
        // create test user and confirm default role
        val user: User =
            userService.createUser(User(username = "testUser4", password = "123456", email = "test4@example.com"))
                ?: fail("user is null")
        Assertions.assertEquals(Role.MEMBER, user.role)

        //change role to mod
        var changedUser = userRoleService.changeRole(user, Role.MODERATOR)
        Assertions.assertEquals(Role.MODERATOR, changedUser?.role)

        //change role to admin
        changedUser = userRoleService.changeRole(user, Role.ADMINISTRATOR)
        Assertions.assertEquals(Role.ADMINISTRATOR, changedUser?.role)

        //change role to suspended
        changedUser = userRoleService.changeRole(user, Role.SUSPENDED)
        Assertions.assertEquals(Role.SUSPENDED, changedUser?.role)
    }

    @Test
    fun changeRoleByUsername() {
        // create test user and confirm default role
        var user =
            userService.createUser(User(username = "testUser94", password = "123456", email = "test94@example.com"))
                ?: fail("user is null")
        Assertions.assertEquals(Role.MEMBER, user.role)
        val userByUserName = User(username = "testUser94", password = "", email = "")

        //change role to mod
        user = userRoleService.changeRole(userByUserName, Role.MODERATOR) ?: fail("User was null")
        Assertions.assertEquals(Role.MODERATOR, user.role)

        //change role to admin
        user = userRoleService.changeRole(userByUserName, Role.ADMINISTRATOR) ?: fail("User was null")
        Assertions.assertEquals(Role.ADMINISTRATOR, user.role)

        //change role to suspended
        user = userRoleService.changeRole(userByUserName, Role.SUSPENDED) ?: fail("User was null")
        Assertions.assertEquals(Role.SUSPENDED, user.role)
    }

    @Test
    fun changeRoleUnknownUser() {
        //create not persisted user
        val user = User(username = "test", password = "123456", email = "test@example.com")
        val result = userRoleService.changeRole(user, Role.MODERATOR)
        Assertions.assertNull(result)
    }
}
