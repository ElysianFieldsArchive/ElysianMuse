//[elysianmuse](../../../index.md)/[org.darkSolace.muse.statistics.service](../index.md)/[LastSeenService](index.md)
/[getByUsername](get-by-username.md)

# getByUsername

[jvm]\
fun [getByUsername](get-by-username.md)(
userName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md)
?

Retrieves a [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md) for a
given [User](../../org.darkSolace.muse.user.model/-user/index.md), identified by its username

#### Return

the [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md) for the
specified [User](../../org.darkSolace.muse.user.model/-user/index.md) or null

## Parameters

jvm

| | |
|---|---|
| userName | <ul><li>the username of a [User](../../org.darkSolace.muse.user.model/-user/index.md) to be used to look up the [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md)</li></ul> |
