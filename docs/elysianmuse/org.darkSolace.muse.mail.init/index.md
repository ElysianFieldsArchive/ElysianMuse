//[elysianmuse](../../index.md)/[org.darkSolace.muse.mail.init](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [MailSettingInitializer](-mail-setting-initializer/index.md) | [jvm]<br>@Component<br>class [MailSettingInitializer](-mail-setting-initializer/index.md)(@AutowiredmailerSettingsService: [MailerSettingsService](../org.darkSolace.muse.mail.service/-mailer-settings-service/index.md), @AutowiredmailerSettingsRepository: [MailerSettingsRepository](../org.darkSolace.muse.mail.repository/-mailer-settings-repository/index.md)) : ApplicationRunner<br>Generates dummy [MailerSettings](../org.darkSolace.muse.mail.model/-mailer-settings/index.md) on startup, if no other [MailerSettings](../org.darkSolace.muse.mail.model/-mailer-settings/index.md) exists. |
| [MailTemplateInitializer](-mail-template-initializer/index.md) | [jvm]<br>@Component<br>class [MailTemplateInitializer](-mail-template-initializer/index.md) : ApplicationRunner<br>Generates build in [MailTemplate](../org.darkSolace.muse.mail.model/-mail-template/index.md)s on startup, if they don't exist already. |
