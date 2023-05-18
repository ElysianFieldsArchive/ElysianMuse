//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.controller](../index.md)/[MailController](index.md)/[updateMailerSettings](update-mailer-settings.md)

# updateMailerSettings

[jvm]\

@PostMapping(value = [&quot;/settings&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('ADMINISTRATOR')&quot;)

fun [updateMailerSettings](update-mailer-settings.md)(@Valid@RequestBodymailerSettings: [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md))

Updates the [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md) used to send emails. New settings are validated rudimentary.

Expects a [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md) object as JSON as the request body

#### Samples
