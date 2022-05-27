//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.service](../index.md)/[PrivateMessageService](index.md)

# PrivateMessageService

[jvm]\
@Service

class [PrivateMessageService](index.md)(@Autowiredval privateMessageRepository: [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md))

## Constructors

| | |
|---|---|
| [PrivateMessageService](-private-message-service.md) | [jvm]<br>fun [PrivateMessageService](-private-message-service.md)(@AutowiredprivateMessageRepository: [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteMessage](delete-message.md) | [jvm]<br>fun [deleteMessage](delete-message.md)(message: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)) |
| [getNumberOfUnreadMessages](get-number-of-unread-messages.md) | [jvm]<br>fun [getNumberOfUnreadMessages](get-number-of-unread-messages.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getReceivedMessagesForUser](get-received-messages-for-user.md) | [jvm]<br>fun [getReceivedMessagesForUser](get-received-messages-for-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)&gt; |
| [getSentMessagesByUser](get-sent-messages-by-user.md) | [jvm]<br>fun [getSentMessagesByUser](get-sent-messages-by-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)&gt; |
| [markMessageAsRead](mark-message-as-read.md) | [jvm]<br>fun [markMessageAsRead](mark-message-as-read.md)(message: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)) |
| [sendMessage](send-message.md) | [jvm]<br>fun [sendMessage](send-message.md)(sentMessage: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [privateMessageRepository](private-message-repository.md) | [jvm]<br>val [privateMessageRepository](private-message-repository.md): [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md) |
