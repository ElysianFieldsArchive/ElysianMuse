//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.repository](../index.md)/[PrivateMessageRepository](index.md)/[findAllByInReplyTo](find-all-by-in-reply-to.md)

# findAllByInReplyTo

[jvm]\

@Transactional

abstract fun [findAllByInReplyTo](find-all-by-in-reply-to.md)(reply: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)&gt;

Retrieves all messages in reply to a given [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)

#### Return

a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)s in reply to the given [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)

## Parameters

jvm

| | |
|---|---|
| reply | the [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md) for which replies are retrieves |
