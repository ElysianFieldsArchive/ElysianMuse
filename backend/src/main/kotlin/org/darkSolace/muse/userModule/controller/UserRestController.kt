package org.darkSolace.muse.userModule.controller

import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * RestController defining endpoints regarding all user activity
 * Subject to change
 */
@RestController
@RequestMapping("/api/user")
class UserRestController(@Autowired val userService: UserService) {

    /**
     * Retrieves a user by its id. Listens on /api/user/{id}.
     *
     * @sample `curl localhost:8080/api/user/5`
     * @param id the user id
     * @return the retrieved [User] or `null`
     */
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): User? {
        return userService.getById(id)
    }

    /**
     * Retrieves all users. Listens on /api/user/all.
     *
     * @sample `curl localhost:8080/api/user/all`
     * @return a List of [User]s - might be empty
     */
    @GetMapping("/all")
    fun getAllUsers(): List<User> = userService.getAll()
}
