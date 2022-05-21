//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.service](../index.md)/[MailService](index.md)

# MailService

[jvm]\
@Service

class [MailService](index.md)(@AutowiredmailTemplateRepository: [MailTemplateRepository](../../org.darkSolace.muse.mail.repository/-mail-template-repository/index.md), @AutowiredmailQueueRepository: [MailQueueRepository](../../org.darkSolace.muse.mail.repository/-mail-queue-repository/index.md), @AutowiredmailerSettingsService: [MailerSettingsService](../-mailer-settings-service/index.md), @AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md))

Service for all mail related tasks.

## Constructors

| | |
|---|---|
| [MailService](-mail-service.md) | [jvm]<br>fun [MailService](-mail-service.md)(@AutowiredmailTemplateRepository: [MailTemplateRepository](../../org.darkSolace.muse.mail.repository/-mail-template-repository/index.md), @AutowiredmailQueueRepository: [MailQueueRepository](../../org.darkSolace.muse.mail.repository/-mail-queue-repository/index.md), @AutowiredmailerSettingsService: [MailerSettingsService](../-mailer-settings-service/index.md), @AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [confirmEMailByCode](confirm-e-mail-by-code.md) | [jvm]<br>fun [confirmEMailByCode](confirm-e-mail-by-code.md)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [enqueueMail](enqueue-mail.md) | [jvm]<br>fun [enqueueMail](enqueue-mail.md)(mail: [Mail](../../org.darkSolace.muse.mail.model/-mail/index.md))<br>Enqueues a mail to be sent on the next scheduler run |
| [generateEMailConfirmUrl](generate-e-mail-confirm-url.md) | [jvm]<br>fun [generateEMailConfirmUrl](generate-e-mail-confirm-url.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md), siteUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Generates a confirm-url used to verify an email address. |
| [generateResetPasswordUrl](generate-reset-password-url.md) | [jvm]<br>fun [generateResetPasswordUrl](generate-reset-password-url.md)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), siteUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Generates a password reset url |
| [markEMailAsValid](mark-e-mail-as-valid.md) | [jvm]<br>fun [markEMailAsValid](mark-e-mail-as-valid.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>fun [markEMailAsValid](mark-e-mail-as-valid.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md))<br>Marks the email of the given user as validated and cleans up all data regarding the (previously outstanding) validation |
| [sendEMailConfirmationMail](send-e-mail-confirmation-mail.md) | [jvm]<br>fun [sendEMailConfirmationMail](send-e-mail-confirmation-mail.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md))<br>Sends an email confirmation message to a newly registered user based on a template. |
| [sendMails](send-mails.md) | [jvm]<br>@Scheduled(cron = &quot;*/10 * * * * *&quot;)<br>fun [sendMails](send-mails.md)() |
| [sendPasswordResetMail](send-password-reset-mail.md) | [jvm]<br>fun [sendPasswordResetMail](send-password-reset-mail.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md), code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Sends a password reset email to the specified user containing a reset code. |

## Properties

| Name | Summary |
|---|---|
| [mailEnabled](mail-enabled.md) | [jvm]<br>var [mailEnabled](mail-enabled.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true |
