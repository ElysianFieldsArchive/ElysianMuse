//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.model.dto](../index.md)/[PrivateMessageDTO](index.md)

# PrivateMessageDTO

[jvm]\
class [PrivateMessageDTO](index.md)

## Constructors

|                                                |                                                               |
|------------------------------------------------|---------------------------------------------------------------|
| [PrivateMessageDTO](-private-message-d-t-o.md) | [jvm]<br>fun [PrivateMessageDTO](-private-message-d-t-o.md)() |

## Types

| Name                             | Summary                                          |
|----------------------------------|--------------------------------------------------|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name                        | Summary                                                                                                                                    |
|-----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| [content](content.md)       | [jvm]<br>var [content](content.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)                       |
| [direction](direction.md)   | [jvm]<br>var [direction](direction.md): [MessageDirection](../../org.darkSolace.muse.privMessages.model/-message-direction/index.md)       |
| [id](id.md)                 | [jvm]<br>var [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null                             |
| [inReplyTo](in-reply-to.md) | [jvm]<br>var [inReplyTo](in-reply-to.md): [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)? = null |
| [isRead](is-read.md)        | [jvm]<br>var [isRead](is-read.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false              |
| [recipient](recipient.md)   | [jvm]<br>var [recipient](recipient.md): [UserIdNameDTO](../../org.darkSolace.muse.user.model.dto/-user-id-name-d-t-o/index.md)? = null     |
| [sender](sender.md)         | [jvm]<br>var [sender](sender.md): [UserIdNameDTO](../../org.darkSolace.muse.user.model.dto/-user-id-name-d-t-o/index.md)? = null           |
| [sentDate](sent-date.md)    | [jvm]<br>var [sentDate](sent-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)                               |
| [subject](subject.md)       | [jvm]<br>var [subject](subject.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)                       |
