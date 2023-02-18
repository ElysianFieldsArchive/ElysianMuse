//[elysianmuse](../../index.md)/[org.darkSolace.muse.mail.service](index.md)

# Package org.darkSolace.muse.mail.service

## Types

| Name | Summary |
|---|---|
| [MailerSettingsService](-mailer-settings-service/index.md) | [jvm]<br>@Service<br>class [MailerSettingsService](-mailer-settings-service/index.md)<br>Service class for [MailerSettings](../org.darkSolace.muse.mail.model/-mailer-settings/index.md) related tasks. Also holds the current JavaMailSender to be used to send EMails. |
| [MailService](-mail-service/index.md) | [jvm]<br>@Service<br>class [MailService](-mail-service/index.md)(@AutowiredmailTemplateRepository: [MailTemplateRepository](../org.darkSolace.muse.mail.repository/-mail-template-repository/index.md), @AutowiredmailQueueRepository: [MailQueueRepository](../org.darkSolace.muse.mail.repository/-mail-queue-repository/index.md), @AutowiredmailerSettingsService: [MailerSettingsService](-mailer-settings-service/index.md), @AutowireduserRepository: [UserRepository](../org.darkSolace.muse.user.repository/-user-repository/index.md))<br>Service for all mail related tasks. |
