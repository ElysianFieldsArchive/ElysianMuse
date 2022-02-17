package org.darkSolace.muse.user

import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
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

    @Test
    fun suspendUser() {
        val user = userService.createUser(User(username = "testUser3", password = "123", email = "test3@example.com"))
            ?: fail("user is null")
        Assertions.assertNotEquals(Role.SUSPENDED, user.role)
        userRoleService.suspendUser(user)
        Assertions.assertEquals(Role.SUSPENDED, user.role)
    }

    @Test
    fun changeRole() {
        // create test user and confirm default role
        val user = userService.createUser(User(username = "testUser4", password = "123", email = "test4@example.com"))
            ?: fail("user is null")
        Assertions.assertEquals(Role.MEMBER, user.role)

        //change role to mod
        userRoleService.changeRole(user, Role.MODERATOR)
        Assertions.assertEquals(Role.MODERATOR, user.role)

        //change role to admin
        userRoleService.changeRole(user, Role.ADMINISTRATOR)
        Assertions.assertEquals(Role.ADMINISTRATOR, user.role)

        //change role to suspended
        userRoleService.changeRole(user, Role.SUSPENDED)
        Assertions.assertEquals(Role.SUSPENDED, user.role)
    }

    @Test
    fun changeRoleByUsername() {
        // create test user and confirm default role
        var user = userService.createUser(User(username = "testUser94", password = "123", email = "test94@example.com"))
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
}
