package org.darkSolace.muse.security.model

/**
 * [SignUpRequest] model containing username, password and email to create a new user account
 * @See [org.darkSolace.muse.user.service.UserService.createUser]
 */
data class SignUpRequest(val username: String, val password: String, val email: String)
