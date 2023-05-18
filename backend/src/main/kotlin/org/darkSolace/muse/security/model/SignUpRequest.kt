package org.darkSolace.muse.security.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

/**
 * [SignUpRequest] model containing username, password and email to create a new user account
 * @See [org.darkSolace.muse.user.service.UserService.createUser]
 */
class SignUpRequest(
    @field:Size(min = 3) val username: String,
    @field:Size(min = 6) val password: String,
    @field:Email val email: String
)
