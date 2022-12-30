//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.model](../index.md)/[MailerSettings](index.md)

# MailerSettings

[jvm]\
@Entity

@Validated

data class [MailerSettings](index.md)(var id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null)

Holds the settings for a smtp server to be used to send emails

#### See also

jvm

| |
|---|
| [MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md) |

## Constructors

| | |
|---|---|
| [MailerSettings](-mailer-settings.md) | [jvm]<br>fun [MailerSettings](-mailer-settings.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null) |

## Functions

| Name | Summary |
|---|---|
| [equals](equals.md) | [jvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [jvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [auth](auth.md) | [jvm]<br>val [auth](auth.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true |
| [fromAddress](from-address.md) | [jvm]<br>var [fromAddress](from-address.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [host](host.md) | [jvm]<br>@NotNull<br>var [host](host.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [jvm]<br>var [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null |
| [password](password.md) | [jvm]<br>@NotNull<br>var [password](password.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [port](port.md) | [jvm]<br>@Min(value = 1)<br>var [port](port.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [protocol](protocol.md) | [jvm]<br>val [protocol](protocol.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [startTLS](start-t-l-s.md) | [jvm]<br>val [startTLS](start-t-l-s.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true |
| [username](username.md) | [jvm]<br>@NotNull<br>var [username](username.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
