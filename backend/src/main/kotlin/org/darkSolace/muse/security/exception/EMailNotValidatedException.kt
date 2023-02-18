package org.darkSolace.muse.security.exception

/**
 * Exception to be thrown if a user is trying to log in while its email address is not validated.
 */
class EMailNotValidatedException(message: String = "") : Exception(message)
