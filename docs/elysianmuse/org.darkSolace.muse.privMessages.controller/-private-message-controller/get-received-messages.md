//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)/[getReceivedMessages](get-received-messages.md)

# getReceivedMessages

[jvm]\

@GetMapping(value = [&quot;/{user}&quot;])

fun [getReceivedMessages](get-received-messages.md)(@PathVariable@Validuser: [User](../../org.darkSolace.muse.user.model/-user/index.md), authentication: Authentication?): ResponseEntity&lt;[Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[PrivateMessageDTO](../../org.darkSolace.muse.privMessages.model.dto/-private-message-d-t-o/index.md)&gt;&gt;

Retrieves all retrieved private messages for the specified user. Only accessible for the own user.

#### Return

list of private messages or HTTP 401 Unauthorized if messages for a different user are retrieved

#### Parameters

jvm

| | |
|---|---|
| user | the user which received messages are retrieved |
| authentication | the authentication used for this request, to verify the right user is logged in |

#### Samples
