//[elysianmuse](../../../index.md)/[org.darkSolace.muse.statistics.service](../index.md)/[LastSeenService](index.md)/[updateLastSeen](update-last-seen.md)

# updateLastSeen

[jvm]\
fun [updateLastSeen](update-last-seen.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, jSession: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)

Updates a [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md), identified either by username (if the user is logged-in) or by session ID

## Parameters

jvm

| | |
|---|---|
| username | <ul><li>the username to identify the [User](../../org.darkSolace.muse.user.model/-user/index.md)</li></ul> |
| jSession | <ul><li>the session ID to identify a visitor</li></ul> |
