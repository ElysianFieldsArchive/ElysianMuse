//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)

# PrivateMessageController

[jvm]\
@RestController

@RequestMapping(value = [&quot;/api/message&quot;])

class [PrivateMessageController](index.md)(@Autowiredval privateMessageService: [PrivateMessageService](../../org.darkSolace.muse.privMessages.service/-private-message-service/index.md), @Autowiredval privateMessageRepository: [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md))

## Constructors

| | |
|---|---|
| [PrivateMessageController](-private-message-controller.md) | [jvm]<br>fun [PrivateMessageController](-private-message-controller.md)(@AutowiredprivateMessageService: [PrivateMessageService](../../org.darkSolace.muse.privMessages.service/-private-message-service/index.md), @AutowiredprivateMessageRepository: [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteMessage](delete-message.md) | [jvm]<br>@DeleteMapping(value = [&quot;/{message}&quot;])<br>fun [deleteMessage](delete-message.md)(@PathVariablemessage: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md), authentication: Authentication?) |
| [getReceivedMessages](get-received-messages.md) | [jvm]<br>@GetMapping(value = [&quot;/{user}&quot;])<br>fun [getReceivedMessages](get-received-messages.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md), authentication: Authentication?): ResponseEntity&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)&gt;&gt; |
| [getSentMessages](get-sent-messages.md) | [jvm]<br>@GetMapping(value = [&quot;/{user}/sent&quot;])<br>fun [getSentMessages](get-sent-messages.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md), authentication: Authentication?): ResponseEntity&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)&gt;&gt; |
| [markMessageAsRead](mark-message-as-read.md) | [jvm]<br>@PostMapping(value = [&quot;/read/{id}&quot;])<br>fun [markMessageAsRead](mark-message-as-read.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt; |
| [numberOfUnreadMessages](number-of-unread-messages.md) | [jvm]<br>@GetMapping(value = [&quot;/unread&quot;])<br>fun [numberOfUnreadMessages](number-of-unread-messages.md)(authentication: Authentication?): ResponseEntity&lt;[Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt; |
| [sendMessage](send-message.md) | [jvm]<br>@PostMapping(value = [&quot;/send&quot;])<br>fun [sendMessage](send-message.md)(@RequestBodymessage: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt; |

## Properties

| Name | Summary |
|---|---|
| [privateMessageRepository](private-message-repository.md) | [jvm]<br>val [privateMessageRepository](private-message-repository.md): [PrivateMessageRepository](../../org.darkSolace.muse.privMessages.repository/-private-message-repository/index.md) |
| [privateMessageService](private-message-service.md) | [jvm]<br>val [privateMessageService](private-message-service.md): [PrivateMessageService](../../org.darkSolace.muse.privMessages.service/-private-message-service/index.md) |
