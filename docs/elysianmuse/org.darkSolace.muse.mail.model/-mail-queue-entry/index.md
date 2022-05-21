//[elysianmuse](../../../index.md)/[org.darkSolace.muse.mail.model](../index.md)/[MailQueueEntry](index.md)

# MailQueueEntry

[jvm]\
@Entity

class [MailQueueEntry](index.md)(val id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, val mail: SimpleMailMessage? = null, val enqueueDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) = Date())

Represents a queued email to be sent at the next scheduled opportunity

## Constructors

| | |
|---|---|
| [MailQueueEntry](-mail-queue-entry.md) | [jvm]<br>fun [MailQueueEntry](-mail-queue-entry.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, mail: SimpleMailMessage? = null, enqueueDate: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) = Date()) |

## Properties

| Name | Summary |
|---|---|
| [enqueueDate](enqueue-date.md) | [jvm]<br>val [enqueueDate](enqueue-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) |
| [id](id.md) | [jvm]<br>val [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null |
| [mail](mail.md) | [jvm]<br>val [mail](mail.md): SimpleMailMessage? = null |
