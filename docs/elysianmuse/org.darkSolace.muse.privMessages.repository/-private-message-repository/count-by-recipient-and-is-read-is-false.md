//[elysianmuse](../../../index.md)/[org.darkSolace.muse.privMessages.repository](../index.md)/[PrivateMessageRepository](index.md)/[countByRecipientAndIsReadIsFalse](count-by-recipient-and-is-read-is-false.md)

# countByRecipientAndIsReadIsFalse

[jvm]\
abstract fun [countByRecipientAndIsReadIsFalse](count-by-recipient-and-is-read-is-false.md)(recipient: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)

Retrieves the number of unread messassed for a given [User](../../org.darkSolace.muse.user.model/-user/index.md) entered as recipient.

#### Return

the number of unread messages

#### Parameters

jvm

| | |
|---|---|
| recipient | the [User](../../org.darkSolace.muse.user.model/-user/index.md), entered as recipient, for which unread messages should be counted. |
