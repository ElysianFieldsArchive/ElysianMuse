//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.init](../index.md)/[MailSettingInitializer](index.md)

# MailSettingInitializer

[jvm]\
@Component

class [MailSettingInitializer](index.md)(@AutowiredmailerSettingsService: [MailerSettingsService](../../org.darkSolace.muse.mail.service/-mailer-settings-service/index.md), @AutowiredmailerSettingsRepository: [MailerSettingsRepository](../../org.darkSolace.muse.mail.repository/-mailer-settings-repository/index.md)) : ApplicationRunner

Generates dummy [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md) on startup, if no other [MailerSettings](../../org.darkSolace.muse.mail.model/-mailer-settings/index.md) exists.

## Constructors

| | |
|---|---|
| [MailSettingInitializer](-mail-setting-initializer.md) | [jvm]<br>constructor(@AutowiredmailerSettingsService: [MailerSettingsService](../../org.darkSolace.muse.mail.service/-mailer-settings-service/index.md), @AutowiredmailerSettingsRepository: [MailerSettingsRepository](../../org.darkSolace.muse.mail.repository/-mailer-settings-repository/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [run](run.md) | [jvm]<br>open override fun [run](run.md)(args: ApplicationArguments?) |
