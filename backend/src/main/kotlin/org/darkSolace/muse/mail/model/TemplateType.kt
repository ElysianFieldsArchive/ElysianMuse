package org.darkSolace.muse.mail.model

/**
 * Enum specifying default, system generated [MailTemplate]s
 */
enum class TemplateType(val templateName: String, val description: String) {
    SIGN_UP_CONFIRMATION(
        "Sign Up Mail Confirmation",
        "Mail template to be sent to a newly registered user to confirm their email address."
    ),
    FORGOT_PASSWORD("Password Reset", "Mail template to be sent to user if 'forgot password' is triggered."),
}
