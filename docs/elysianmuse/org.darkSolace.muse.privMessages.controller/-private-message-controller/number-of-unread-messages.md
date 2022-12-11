//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.controller](../index.md)/[PrivateMessageController](index.md)/[numberOfUnreadMessages](number-of-unread-messages.md)

# numberOfUnreadMessages

[jvm]\

@GetMapping(value = [&quot;/unread&quot;])

fun [numberOfUnreadMessages](number-of-unread-messages.md)(authentication: Authentication?): ResponseEntity&lt;[Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)&gt;

Retrieves the number of unread messages for the given user. The user is derived from the authentication used

#### Return

number of unread messages

## Samples

## Parameters

jvm

| | |
|---|---|
| authentication | the authentication used for this request, to verify the right user is logged in |
