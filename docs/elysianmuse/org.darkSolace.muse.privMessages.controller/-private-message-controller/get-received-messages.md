//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)/[getReceivedMessages](get-received-messages.md)

# getReceivedMessages

[jvm]\

@GetMapping(value = [&quot;/{user}&quot;])

fun [getReceivedMessages](get-received-messages.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md), authentication: Authentication?): ResponseEntity&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)&gt;&gt;

Retrieves all retrieved private messages for the specified user. Only accessible for the own user.

#### Return

list of private messages or HTTP 401 Unauthorized if messages for a different user are retrieved

## Samples

## Parameters

jvm

| | |
|---|---|
| user | the user which received messages are retrieved |
| authentication | the authentication used for this request, to verify the right user is logged in |
