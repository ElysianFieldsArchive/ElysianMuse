package org.darkSolace.muse.security.model

/**
 * [LoginRequest] model containing username and password used to sign in
 * @see [org.darkSolace.muse.security.controller.AuthController.authenticateUser]
 */
data class LoginRequest(val username: String, val password: String)
