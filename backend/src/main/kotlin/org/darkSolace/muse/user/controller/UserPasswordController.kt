package org.darkSolace.muse.user.controller

import org.darkSolace.muse.user.service.UserPasswordService
import org.darkSolace.muse.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * RestController defining endpoints regarding all password reset activities
 */
@RestController
@RequestMapping("/api/user")
class UserPasswordController(
    @Autowired val userService: UserService,
    @Autowired val userPasswordService: UserPasswordService,
) {
    /**
     * Updates a users password if a valid [org.darkSolace.muse.user.model.PasswordResetRequest] exists.
     *
     * @sample `curl -X POST -d '...' localhost:8080/api/reset/9df2cc31-f733-4daa-8277-d3c0afdb1a5a`
     *
     * @param passwordConfirmationCode confirmation code to verify that the reset request is valid
     * @param newPassword new password to be set, sent as the request body
     * @return HTTP 200 (OK) on success, 400 (BAD REQUEST) otherwise
     */
    @PostMapping("/reset/{passwordConfirmationCode}")
    fun resetPassword(
        @PathVariable passwordConfirmationCode: String,
        @RequestBody newPassword: String,
    ): ResponseEntity<Unit> {
        val user = userPasswordService.getUserByCode(passwordConfirmationCode)

        return if (newPassword.isBlank() || user == null) {
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        } else {
            userPasswordService.updatePassword(user, newPassword)
            ResponseEntity<Unit>(HttpStatus.OK)
        }
    }

    /**
     * Request a password reset for a user, identified either by email or username
     *
     * @param identifier the identifying user information - either username or email
     *
     * @return HTTP 200 (OK) on success, 400 (BAD REQUEST) otherwise
     */
    @PostMapping("/reset")
    fun requestPasswordResetByEmailOrUsername(@RequestBody identifier: String): ResponseEntity<Unit> {
        val user = if (identifier.contains("@")) {
            userService.userRepository.findByEmail(identifier)
        } else {
            userService.userRepository.findByUsername(identifier)
        }

        return if (user != null) {
            userPasswordService.generateAndSendPasswordResetCode(user)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}
