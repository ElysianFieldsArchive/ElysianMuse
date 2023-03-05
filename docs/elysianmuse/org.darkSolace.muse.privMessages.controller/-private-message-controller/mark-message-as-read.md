//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)/[markMessageAsRead](mark-message-as-read.md)

# markMessageAsRead

[jvm]\

@PostMapping(value = [&quot;/read/{id}&quot;])

fun [markMessageAsRead](mark-message-as-read.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Marks a private message as read

#### Return

HTTP OK or UNAUTHORIZED, depending on the requests success

#### Parameters

jvm

| | |
|---|---|
| id | id of the message to be marked as read |
| authentication | the authentication used for this request, to verify the right user is logged in |

#### Samples
