package org.darkSolace.muse.securityModule

import org.darkSolace.muse.securityModule.model.UserDetails
import org.darkSolace.muse.securityModule.service.UserDetailsService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class UserDetailsServiceTests : TestBase() {
    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var userService: UserService

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
