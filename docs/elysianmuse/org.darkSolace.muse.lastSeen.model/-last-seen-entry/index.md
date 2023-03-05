//[elysianmuse](../../../index.md)/[org.darkSolace.muse.lastSeen.model](../index.md)/[LastSeenEntry](index.md)

# LastSeenEntry

[jvm]\
@Entity

class [LastSeenEntry](index.md)

The [LastSeenEntry](index.md) class containing visitor session information, a user if the visitor is logged in as well a timestamp when the visitor was last seen.

## Constructors

| | |
|---|---|
| [LastSeenEntry](-last-seen-entry.md) | [jvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [id](id.md) | [jvm]<br>val [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) = 0 |
| [jSession](j-session.md) | [jvm]<br>var [jSession](j-session.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [lastSeen](last-seen.md) | [jvm]<br>var [lastSeen](last-seen.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) |
| [user](user.md) | [jvm]<br>var [user](user.md): [User](../../org.darkSolace.muse.user.model/-user/index.md)? |
