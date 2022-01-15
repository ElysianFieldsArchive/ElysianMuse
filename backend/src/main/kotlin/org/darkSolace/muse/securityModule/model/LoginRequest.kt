package org.darkSolace.muse.securityModule.model

/**
 * [LoginRequest] model containing username and password used to sign in
 * @see [org.darkSolace.muse.securityModule.controller.AuthController.authenticateUser]
 */
data class LoginRequest(val username: String, val password: String)
