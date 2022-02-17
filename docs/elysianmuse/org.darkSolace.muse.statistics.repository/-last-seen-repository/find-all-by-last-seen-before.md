//[elysianmuse](../../../index.md)/[org.darkSolace.muse.statistics.repository](../index.md)/[LastSeenRepository](index.md)/[findAllByLastSeenBefore](find-all-by-last-seen-before.md)

# findAllByLastSeenBefore

[jvm]\
abstract fun [findAllByLastSeenBefore](find-all-by-last-seen-before.md)(timeout: [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md)&gt;

Retrieves all [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md) before a specific timestamp, used to determine timed-out sessions

#### Return

List of all [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md)s older than the provided timeout [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)

## Parameters

jvm

| | |
|---|---|
| timeout | <ul><li>[Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html), used to filter [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md)s older than the specified date</li></ul> |
