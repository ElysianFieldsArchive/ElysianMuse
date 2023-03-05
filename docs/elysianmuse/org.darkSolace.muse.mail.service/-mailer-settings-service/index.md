//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.service](../index.md)/[MailerSettingsService](index.md)

# MailerSettingsService

[jvm]\
@Service

class [MailerSettingsService](index.md)

Service class for [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md) related tasks. Also holds the current JavaMailSender to be used to send EMails.

## Constructors

| | |
|---|---|
| [MailerSettingsService](-mailer-settings-service.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [getCurrentSettings](get-current-settings.md) | [jvm]<br>fun [getCurrentSettings](get-current-settings.md)(): [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md)<br>Getter for the currently used [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md) |
| [getMailer](get-mailer.md) | [jvm]<br>fun [getMailer](get-mailer.md)(): JavaMailSender<br>Getter for the current JavaMailSender |
| [getSenderAddress](get-sender-address.md) | [jvm]<br>fun [getSenderAddress](get-sender-address.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Getter for the current sender address used when sending mails. Can be changed by applying a new [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md). |
| [updateMailerSettings](update-mailer-settings.md) | [jvm]<br>@Transactional<br>fun [updateMailerSettings](update-mailer-settings.md)(@ValidnewSettings: [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md))<br>Updates the [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md) and generates a new JavaMailSender to be used to send mails after the update |
