//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.service](../index.md)/[MailerSettingsService](index.md)/[updateMailerSettings](update-mailer-settings.md)

# updateMailerSettings

[jvm]\

@Transactional

fun [updateMailerSettings](update-mailer-settings.md)(@ValidnewSettings: [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md))

Updates the [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md) and generates a new JavaMailSender to be used to send mails after the update

## Parameters

jvm

| | |
|---|---|
| newSettings | the new [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md) to be used |
