//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.repository](../index.md)/[PrivateMessageRepository](index.md)/[findAllBySenderAndDirection](find-all-by-sender-and-direction.md)

# findAllBySenderAndDirection

[jvm]\

@Transactional

abstract fun [findAllBySenderAndDirection](find-all-by-sender-and-direction.md)(sender: [User](../../org.darkSolace.muse.user.model/-user/index.md), direction: [MessageDirection](../../org.darkSolace.muse.privMessages.model/-message-direction/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)&gt;

Retrieves all [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)s for a given [User](../../org.darkSolace.muse.user.model/-user/index.md) as sender. Either send or received depending on the [MessageDirection](../../org.darkSolace.muse.privMessages.model/-message-direction/index.md)

#### Return

a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)s

## Parameters

jvm

| | |
|---|---|
| sender | the [User](../../org.darkSolace.muse.user.model/-user/index.md) entered as sender in the messages to be received |
| direction | the [MessageDirection](../../org.darkSolace.muse.privMessages.model/-message-direction/index.md) to retrieve sent or received messages |
