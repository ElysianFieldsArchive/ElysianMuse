//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.model](../index.md)/[PrivateMessage](index.md)

# PrivateMessage

[jvm]\
@Entity

data class [PrivateMessage](index.md)(var id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, var direction: [MessageDirection](../-message-direction/index.md) = MessageDirection.OUTGOING, var sender: [User](../../org.darkSolace.muse.user.model/-user/index.md), var recipient: [User](../../org.darkSolace.muse.user.model/-user/index.md), var subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var content: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var sentDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) = Date(), var isRead: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, var inReplyTo: [PrivateMessage](index.md)? = null)

This [PrivateMessage](index.md) class holds all information used for direct communication between [User](../../org.darkSolace.muse.user.model/-user/index.md)s.

## Constructors

| | |
|---|---|
| [PrivateMessage](-private-message.md) | [jvm]<br>constructor(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, direction: [MessageDirection](../-message-direction/index.md) = MessageDirection.OUTGOING, sender: [User](../../org.darkSolace.muse.user.model/-user/index.md), recipient: [User](../../org.darkSolace.muse.user.model/-user/index.md), subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, content: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, sentDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) = Date(), isRead: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, inReplyTo: [PrivateMessage](index.md)? = null) |

## Functions

| Name | Summary |
|---|---|
| [equals](equals.md) | [jvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [jvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [content](content.md) | [jvm]<br>var [content](content.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [direction](direction.md) | [jvm]<br>var [direction](direction.md): [MessageDirection](../-message-direction/index.md) |
| [id](id.md) | [jvm]<br>var [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? |
| [inReplyTo](in-reply-to.md) | [jvm]<br>var [inReplyTo](in-reply-to.md): [PrivateMessage](index.md)? |
| [isRead](is-read.md) | [jvm]<br>var [isRead](is-read.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [recipient](recipient.md) | [jvm]<br>var [recipient](recipient.md): [User](../../org.darkSolace.muse.user.model/-user/index.md) |
| [sender](sender.md) | [jvm]<br>var [sender](sender.md): [User](../../org.darkSolace.muse.user.model/-user/index.md) |
| [sentDate](sent-date.md) | [jvm]<br>var [sentDate](sent-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) |
| [subject](subject.md) | [jvm]<br>var [subject](subject.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
