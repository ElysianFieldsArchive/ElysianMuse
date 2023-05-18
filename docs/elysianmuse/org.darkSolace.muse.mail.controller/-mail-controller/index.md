//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.controller](../index.md)/[MailController](index.md)

# MailController

[jvm]\
@RestController

@RequestMapping(value = [&quot;/api/mail&quot;])

@Validated

class [MailController](index.md)(@Autowiredval mailService: [MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md), @Autowiredval mailerSettingsService: [MailerSettingsService](../../org.darkSolace.muse.mail.service/-mailer-settings-service/index.md))

RestController defining endpoints regarding mail settings and user email confirmation

## Constructors

| | |
|---|---|
| [MailController](-mail-controller.md) | [jvm]<br>constructor(@AutowiredmailService: [MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md), @AutowiredmailerSettingsService: [MailerSettingsService](../../org.darkSolace.muse.mail.service/-mailer-settings-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [processEMailConfirmation](process-e-mail-confirmation.md) | [jvm]<br>@GetMapping(value = [&quot;/confirm/{code}&quot;])<br>fun [processEMailConfirmation](process-e-mail-confirmation.md)(@PathVariablecode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Validates a users email address by a validation code sent to the provided email address. |
| [updateMailerSettings](update-mailer-settings.md) | [jvm]<br>@PostMapping(value = [&quot;/settings&quot;])<br>@PreAuthorize(value = &quot;hasAnyAuthority('ADMINISTRATOR')&quot;)<br>fun [updateMailerSettings](update-mailer-settings.md)(@Valid@RequestBodymailerSettings: [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md))<br>Updates the [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md) used to send emails. New settings are validated rudimentary. |

## Properties

| Name | Summary |
|---|---|
| [mailerSettingsService](mailer-settings-service.md) | [jvm]<br>val [mailerSettingsService](mailer-settings-service.md): [MailerSettingsService](../../org.darkSolace.muse.mail.service/-mailer-settings-service/index.md) |
| [mailService](mail-service.md) | [jvm]<br>val [mailService](mail-service.md): [MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md) |
