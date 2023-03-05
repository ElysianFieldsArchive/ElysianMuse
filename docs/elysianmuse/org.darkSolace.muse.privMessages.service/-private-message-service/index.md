//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.service](../index.md)/[PrivateMessageService](index.md)

# PrivateMessageService

[jvm]\
@Service

class [PrivateMessageService](index.md)(@Autowiredval privateMessageRepository: [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md))

Service class for [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md) related tasks.

## Constructors

| | |
|---|---|
| [PrivateMessageService](-private-message-service.md) | [jvm]<br>constructor(@AutowiredprivateMessageRepository: [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteMessage](delete-message.md) | [jvm]<br>fun [deleteMessage](delete-message.md)(message: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md))<br>Deletes a [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md) |
| [getNumberOfUnreadMessages](get-number-of-unread-messages.md) | [jvm]<br>fun [getNumberOfUnreadMessages](get-number-of-unread-messages.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>Retrieves the number of unread messages for a given [User](../../org.darkSolace.muse.user.model/-user/index.md) |
| [getReceivedMessagesForUser](get-received-messages-for-user.md) | [jvm]<br>fun [getReceivedMessagesForUser](get-received-messages-for-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)&gt;<br>Retrieves [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)s retrieved by the provided [User](../../org.darkSolace.muse.user.model/-user/index.md) |
| [getSentMessagesByUser](get-sent-messages-by-user.md) | [jvm]<br>fun [getSentMessagesByUser](get-sent-messages-by-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)&gt;<br>Retrieves [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)s sent by the provided [User](../../org.darkSolace.muse.user.model/-user/index.md) |
| [markMessageAsRead](mark-message-as-read.md) | [jvm]<br>fun [markMessageAsRead](mark-message-as-read.md)(message: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md))<br>Maks a [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md) as read |
| [sendMessage](send-message.md) | [jvm]<br>fun [sendMessage](send-message.md)(sentMessage: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md))<br>Sends a [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md) message. Generates two [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)s - on as sent by the sender, on received by the recipient |

## Properties

| Name | Summary |
|---|---|
| [privateMessageRepository](private-message-repository.md) | [jvm]<br>val [privateMessageRepository](private-message-repository.md): [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md) |
