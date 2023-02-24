package org.darkSolace.muse.security.model

enum class AuthMessages(val message: String) {
    ERROR_EMAIL_IN_USE("Email is already in use!"),
    ERROR_EMAIL_NOT_VALIDATED("Email is not validated!"),
    ERROR_USERNAME_IN_USE("Username is already in use!"),
    ERROR_USERNAME_PASSWORD_WRONG("Unknown username or wrong password!"),
    SUCCESS_USER_CREATE("User created successfully."),
}
