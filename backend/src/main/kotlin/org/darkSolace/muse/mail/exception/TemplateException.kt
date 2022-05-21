package org.darkSolace.muse.mail.exception

/**
 * Exception thrown when a valid url can't be generated for a template (e.g. email confirm url)
 */
class TemplateException(message: String = "") : Exception(message)
