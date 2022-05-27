//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)/[getReceivedMessages](get-received-messages.md)

# getReceivedMessages

[jvm]\

@GetMapping(value = [&quot;/{user}&quot;])

fun [getReceivedMessages](get-received-messages.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md), authentication: Authentication?): ResponseEntity&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PrivateMessage](../../org.darkSolace.muse.privMessages.model/-private-message/index.md)&gt;&gt;
