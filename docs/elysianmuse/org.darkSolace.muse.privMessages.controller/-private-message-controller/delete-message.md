//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)/[deleteMessage](delete-message.md)

# deleteMessage

[jvm]\

@DeleteMapping(value = [&quot;/{message}&quot;])

fun [deleteMessage](delete-message.md)(@PathVariablemessage: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Deletes a private message

#### Samples

#### Parameters

jvm

| | |
|---|---|
| message | the private message to be deleted |
| authentication | the authentication used for this request, to verify the right user is logged in |
