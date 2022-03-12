//[elysianmuse](../../../index.md)/[org.darkSolace.muse.statistics.repository](../index.md)
/[LastSeenRepository](index.md)/[findByjSession](find-byj-session.md)

# findByjSession

[jvm]\
abstract fun [findByjSession](find-byj-session.md)(
jSession: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)
?): [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md)?

Retrieves a [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md) for a given session ID

#### Return

the [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md) for the specified session ID
or null if no [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md) was found

## Parameters

jvm

| | |
|---|---|
| jSession | <ul><li>[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) containing a session ID used to identify the [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md)</li></ul> |
