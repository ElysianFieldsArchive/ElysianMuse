//[elysianmuse](../../index.md)/[org.darkSolace.muse.mail.model](index.md)

# Package org.darkSolace.muse.mail.model

## Types

| Name | Summary |
|---|---|
| [Mail](-mail/index.md) | [jvm]<br>@Entity<br>data class [Mail](-mail/index.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, recipient: [User](../org.darkSolace.muse.user.model/-user/index.md), subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), content: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [MailerSettings](-mailer-settings/index.md) | [jvm]<br>@Entity<br>data class [MailerSettings](-mailer-settings/index.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, host: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), port: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), protocol: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), auth: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), startTLS: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), fromAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [MailQueueEntry](-mail-queue-entry/index.md) | [jvm]<br>@Entity<br>class [MailQueueEntry](-mail-queue-entry/index.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, mail: [Mail](-mail/index.md)?, enqueueDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)) |
| [MailTemplate](-mail-template/index.md) | [jvm]<br>@Entity<br>class [MailTemplate](-mail-template/index.md)(type: [TemplateType](-template-type/index.md)) |
| [MailTemplateVar](-mail-template-var/index.md) | [jvm]<br>@Entity<br>data class [MailTemplateVar](-mail-template-var/index.md)(templateVar: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, system_managed: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [TemplateType](-template-type/index.md) | [jvm]<br>enum [TemplateType](-template-type/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[TemplateType](-template-type/index.md)&gt; |
