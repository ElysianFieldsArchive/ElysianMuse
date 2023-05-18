//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)

# PrivateMessageController

[jvm]\
@RestController

@RequestMapping(value = [&quot;/api/message&quot;])

@Validated

class [PrivateMessageController](index.md)(@Autowiredval privateMessageService: [PrivateMessageService](../../org.darkSolace.muse.privMessages.service/-private-message-service/index.md), @Autowiredval privateMessageRepository: [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md))

RestController defining endpoints regarding private messages between users

## Constructors

| | |
|---|---|
| [PrivateMessageController](-private-message-controller.md) | [jvm]<br>constructor(@AutowiredprivateMessageService: [PrivateMessageService](../../org.darkSolace.muse.privMessages.service/-private-message-service/index.md), @AutowiredprivateMessageRepository: [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteMessage](delete-message.md) | [jvm]<br>@DeleteMapping(value = [&quot;/{message}&quot;])<br>fun [deleteMessage](delete-message.md)(@PathVariable@Validmessage: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Deletes a private message |
| [getReceivedMessages](get-received-messages.md) | [jvm]<br>@GetMapping(value = [&quot;/{user}&quot;])<br>fun [getReceivedMessages](get-received-messages.md)(@PathVariable@Validuser: [User](../../org.darkSolace.muse.user.model/-user/index.md), authentication: Authentication?): ResponseEntity&lt;[Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[PrivateMessageDTO](../../org.darkSolace.muse.privMessages.model.dto/-private-message-d-t-o/index.md)&gt;&gt;<br>Retrieves all retrieved private messages for the specified user. Only accessible for the own user. |
| [getSentMessages](get-sent-messages.md) | [jvm]<br>@GetMapping(value = [&quot;/{user}/sent&quot;])<br>fun [getSentMessages](get-sent-messages.md)(@PathVariable@Validuser: [User](../../org.darkSolace.muse.user.model/-user/index.md), authentication: Authentication?): ResponseEntity&lt;[Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[PrivateMessageDTO](../../org.darkSolace.muse.privMessages.model.dto/-private-message-d-t-o/index.md)&gt;&gt;<br>Retrieves all sent private messages for the specified user. Only accessible for the own user. |
| [markMessageAsRead](mark-message-as-read.md) | [jvm]<br>@PostMapping(value = [&quot;/read/{id}&quot;])<br>fun [markMessageAsRead](mark-message-as-read.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Marks a private message as read |
| [numberOfUnreadMessages](number-of-unread-messages.md) | [jvm]<br>@GetMapping(value = [&quot;/unread&quot;])<br>fun [numberOfUnreadMessages](number-of-unread-messages.md)(authentication: Authentication?): ResponseEntity&lt;[Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt;<br>Retrieves the number of unread messages for the given user. The user is derived from the authentication used |
| [sendMessage](send-message.md) | [jvm]<br>@PostMapping(value = [&quot;/send&quot;])<br>fun [sendMessage](send-message.md)(@RequestBody@Validmessage: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Sends a message to another user |

## Properties

| Name | Summary |
|---|---|
| [privateMessageRepository](private-message-repository.md) | [jvm]<br>val [privateMessageRepository](private-message-repository.md): [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md) |
| [privateMessageService](private-message-service.md) | [jvm]<br>val [privateMessageService](private-message-service.md): [PrivateMessageService](../../org.darkSolace.muse.privMessages.service/-private-message-service/index.md) |
