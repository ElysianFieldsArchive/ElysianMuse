package org.dark_solace.muse.userModule.controller

import org.dark_solace.muse.userModule.model.User
import org.dark_solace.muse.userModule.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserRestController(@Autowired val userService: UserService) {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): User? {
        return userService.getById(id)
    }

    @GetMapping("/all")
    fun getAllUsers(): List<User> = userService.getAll()
}