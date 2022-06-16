//[elysianmuse](../../index.md)/[org.darkSolace.muse.privMessages.model](index.md)

# Package org.darkSolace.muse.privMessages.model

## Types

| Name | Summary |
|---|---|
| [MessageDirection](-message-direction/index.md) | [jvm]<br>enum [MessageDirection](-message-direction/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[MessageDirection](-message-direction/index.md)&gt; <br>Enumeration indication if a [PrivateMessage](-private-message/index.md) was sent or received. |
| [PrivateMessage](-private-message/index.md) | [jvm]<br>@Entity<br>data class [PrivateMessage](-private-message/index.md)(var id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, var direction: [MessageDirection](-message-direction/index.md) = MessageDirection.OUTGOING, var sender: [User](../org.darkSolace.muse.user.model/-user/index.md)? = null, var recipient: [User](../org.darkSolace.muse.user.model/-user/index.md)? = null, var subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var content: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var sentDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) = Date(), var isRead: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, var inReplyTo: [PrivateMessage](-private-message/index.md)? = null)<br>This [PrivateMessage](-private-message/index.md) class holds all information used for direct communication between [User](../org.darkSolace.muse.user.model/-user/index.md)s. |
