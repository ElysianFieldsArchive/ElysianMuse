//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.model](../index.md)/[Mail](index.md)

# Mail

[jvm]\
@Entity

data class [Mail](index.md)(var id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, val recipient: [User](../../org.darkSolace.muse.user.model/-user/index.md), val subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val content: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

This [Mail](index.md) class holds all information required to be sent via email. Mainly used by [MailQueueEntry](../-mail-queue-entry/index.md) to be sent via the [org.darkSolace.muse.mail.service.MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md)

## See also

jvm

| | |
|---|---|
| [org.darkSolace.muse.mail.model.MailQueueEntry](../-mail-queue-entry/index.md) |  |
| [org.darkSolace.muse.mail.service.MailService](../../org.darkSolace.muse.mail.service/-mail-service/index.md) |  |

## Constructors

| | |
|---|---|
| [Mail](-mail.md) | [jvm]<br>fun [Mail](-mail.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, recipient: [User](../../org.darkSolace.muse.user.model/-user/index.md), subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), content: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) |

## Functions

| Name | Summary |
|---|---|
| [equals](equals.md) | [jvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [jvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [content](content.md) | [jvm]<br>val [content](content.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [jvm]<br>var [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null |
| [recipient](recipient.md) | [jvm]<br>val [recipient](recipient.md): [User](../../org.darkSolace.muse.user.model/-user/index.md) |
| [subject](subject.md) | [jvm]<br>val [subject](subject.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
