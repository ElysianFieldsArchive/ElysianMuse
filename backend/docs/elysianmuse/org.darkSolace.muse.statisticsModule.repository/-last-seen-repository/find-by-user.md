//[elysianmuse](../../../index.md)/[org.darkSolace.muse.statisticsModule.repository](../index.md)
/[LastSeenRepository](index.md)/[findByUser](find-by-user.md)

# findByUser

[jvm]\
abstract fun [findByUser](find-by-user.md)(
user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md)): [LastSeenEntry](../../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md)
?

Retrieves a [LastSeenEntry](../../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md) for a
given [User](../../org.darkSolace.muse.userModule.model/-user/index.md)

#### Return

the [LastSeenEntry](../../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md) for the
specified [User](../../org.darkSolace.muse.userModule.model/-user/index.md) or null

## Parameters

jvm

| | |
|---|---|
| user | <ul><li>the [User](../../org.darkSolace.muse.userModule.model/-user/index.md) to be used to look up the [LastSeenEntry](../../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md)</li></ul> |
