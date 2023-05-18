package org.darkSolace.muse.security

import org.darkSolace.muse.security.model.UserDetails
import org.darkSolace.muse.security.service.UserDetailsService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.service.UserService
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
        userService.createUser(User(username = "testUser", password = "123456", email = "test@example.com"))

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
