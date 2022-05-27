//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)/[sendMessage](send-message.md)

# sendMessage

[jvm]\

@PostMapping(value = [&quot;/send&quot;])

fun [sendMessage](send-message.md)(@RequestBodymessage: [PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;
