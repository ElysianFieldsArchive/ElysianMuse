package org.darkSolace.muse.user

import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.service.SuspensionService
import org.darkSolace.muse.user.service.UserRoleService
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class SuspensionServiceTests : TestBase() {
    @Autowired
    private lateinit var suspensionService: SuspensionService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRoleService: UserRoleService

    @Test
    @Order(1)
    fun getSuspensionHistory() {
        val user = userService.createUserFromSignUpRequest(SignUpRequest("test", "123", "test@example.com"))

        var history = suspensionService.getSuspensionHistory(user?.id ?: -1)
        Assertions.assertTrue(history.isEmpty())

        userRoleService.suspendUser(user?.id ?: -1)

        history = suspensionService.getSuspensionHistory(user?.id ?: -1)
        Assertions.assertTrue(history.isNotEmpty())
        Assertions.assertEquals(1, history.count())
    }

    @Test
    @Order(2)
    fun getAllCurrentlySuspendedUsers() {
        val user = userService.createUserFromSignUpRequest(SignUpRequest("test", "123", "test@example.com"))

        var allSuspended = suspensionService.getAllCurrentlySuspendedUsers()
        Assertions.assertTrue(allSuspended.isEmpty())

        userRoleService.suspendUser(user?.id ?: -1)

        allSuspended = suspensionService.getAllCurrentlySuspendedUsers()
        Assertions.assertTrue(allSuspended.isNotEmpty())
        Assertions.assertTrue(allSuspended.contains(user))
    }

    @Test
    @Order(3)
    fun suspensionCodeForUsername() {
        val user = userService.createUserFromSignUpRequest(SignUpRequest("test", "123", "test@example.com"))
        userRoleService.suspendUser(user?.id ?: -1)

        val code = suspensionService.suspensionCodeForUsername(user?.username ?: "")
        Assertions.assertTrue((code?.length ?: -1) > 0)
    }

    @Test
    @Order(4)
    fun suspensionCodeForUsernameUnsuspended() {
        val user = userService.createUserFromSignUpRequest(SignUpRequest("test", "123", "test@example.com"))

        val code = suspensionService.suspensionCodeForUsername(user?.username ?: "")
        Assertions.assertNull(code)
    }

    @Test
    @Order(5)
    fun suspensionCodeForUsernameUnknownUser() {
        val code = suspensionService.suspensionCodeForUsername("unknown")
        Assertions.assertNull(code)
    }
}
