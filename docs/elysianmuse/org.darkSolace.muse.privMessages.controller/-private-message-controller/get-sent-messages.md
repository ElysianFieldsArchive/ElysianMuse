//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)/[getSentMessages](get-sent-messages.md)

# getSentMessages

[jvm]\

@GetMapping(value = [&quot;/{user}/sent&quot;])

fun [getSentMessages](get-sent-messages.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md), authentication: Authentication?): ResponseEntity&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessageDTO](../../org.darkSolace.muse.privMessages.model.dto/-private-message-d-t-o/index.md)&gt;&gt;

Retrieves all sent private messages for the specified user. Only accessible for the own user.

#### Return

list of private messages or HTTP 401 Unauthorized if messages for a different user are retrieved

#### Parameters

jvm

| | |
|---|---|
| user | the user which sent messages are retrieved |
| authentication | the authentication used for this request, to verify the right user is logged in |

#### Samples
