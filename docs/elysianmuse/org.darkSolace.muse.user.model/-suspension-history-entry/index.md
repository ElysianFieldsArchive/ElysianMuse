//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.model](../index.md)/[SuspensionHistoryEntry](index.md)

# SuspensionHistoryEntry

[jvm]\
@Entity

class [SuspensionHistoryEntry](index.md)(val id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, val user: [User](../-user/index.md), val suspendedDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) = Date(), val confirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = UUID.randomUUID().toString(), var acceptedDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null, var confirmationDetails: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

## Constructors

| | |
|---|---|
| [SuspensionHistoryEntry](-suspension-history-entry.md) | [jvm]<br>fun [SuspensionHistoryEntry](-suspension-history-entry.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, user: [User](../-user/index.md), suspendedDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) = Date(), confirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = UUID.randomUUID().toString(), acceptedDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null, confirmationDetails: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [acceptedDate](accepted-date.md) | [jvm]<br>var [acceptedDate](accepted-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)? = null |
| [confirmationCode](confirmation-code.md) | [jvm]<br>val [confirmationCode](confirmation-code.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [confirmationDetails](confirmation-details.md) | [jvm]<br>var [confirmationDetails](confirmation-details.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [id](id.md) | [jvm]<br>val [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? |
| [suspendedDate](suspended-date.md) | [jvm]<br>val [suspendedDate](suspended-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) |
| [user](user.md) | [jvm]<br>val [user](user.md): [User](../-user/index.md) |
