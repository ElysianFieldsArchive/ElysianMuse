//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)/[sendMessage](send-message.md)

# sendMessage

[jvm]\

@PostMapping(value = [&quot;/send&quot;])

fun [sendMessage](send-message.md)(@RequestBody@Validmessage: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Sends a message to another user

#### Return

HTTP OK or UNAUTHORIZED, depending on the requests success

#### Parameters

jvm

| | |
|---|---|
| message | the private message to be sent, passed as the request body |
| authentication | the authentication used for this request, to verify the right user is logged in |

#### Samples
