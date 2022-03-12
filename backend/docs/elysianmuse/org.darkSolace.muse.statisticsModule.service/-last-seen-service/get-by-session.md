//[elysianmuse](../../../index.md)/[org.darkSolace.muse.statisticsModule.service](../index.md)
/[LastSeenService](index.md)/[getBySession](get-by-session.md)

# getBySession

[jvm]\
fun [getBySession](get-by-session.md)(
sessionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)
?): [LastSeenEntry](../../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md)?

Retrieves a [LastSeenEntry](../../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md) for a given
session ID

#### Return

the [LastSeenEntry](../../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md) for the specified
session ID or null if no [LastSeenEntry](../../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md) was
found

## Parameters

jvm

| | |
|---|---|
| sessionId | <ul><li>[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) containing a session ID used to identify the [LastSeenEntry](../../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md)</li></ul> |
