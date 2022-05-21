package org.darkSolace.muse.mail.controller

import org.darkSolace.muse.mail.model.MailerSettings
import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.mail.service.MailerSettingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * RestController defining endpoints regarding mail settings and user email confirmation
 */
@RestController
@RequestMapping("/api/mail")
@Validated
class MailController(
    @Autowired val mailService: MailService,
    @Autowired val mailerSettingsService: MailerSettingsService
) {
    /**
     * Validates a users email address by a validation code sent to the provided email address.
     *
     * @sample `curl localhost:8080/api/mail/confirm/9df2cc31-f733-4daa-8277-d3c0afdb1a5a`
     * @param code the validation code sent to the provided email
     * @return Http status 200 after a successful validation, 400 otherwise
     */
    @GetMapping("/confirm/{code}")
    fun processEMailConfirmation(@PathVariable code: String): ResponseEntity<Unit> {
        val success = mailService.confirmEMailByCode(code)
        return if (success) {
            ResponseEntity.ok(Unit)
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    /**
     * Updates the [MailerSettings] used to send emails.
     * New settings are validated rudimentary.
     *
     * Expects a [MailerSettings] object as JSON as the request body
     * @sample `curl -d '...' localhost:8080/api/mail/settings`
     */
    @PostMapping("/settings")
    @Validated
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    fun updateMailerSettings(
        @Valid @RequestBody mailerSettings: MailerSettings
    ) {
        mailerSettingsService.updateMailerSettings(mailerSettings)
    }
}
