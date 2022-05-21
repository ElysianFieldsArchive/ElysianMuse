//[elysianmuse](../../../index.md)/[org.darkSolace.muse.lastSeen.service](../index.md)/[LastSeenService](index.md)

# LastSeenService

[jvm]\
@Service

class [LastSeenService](index.md)(@Autowiredval userRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @Autowiredval lastSeenRepository: [LastSeenRepository](../../org.darkSolace.muse.lastSeen.repository/-last-seen-repository/index.md), @Value(value = &quot;${session.timeInMinutes}&quot;)val sessionTimeInMinutes: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Service class for [LastSeenEntry](../../org.darkSolace.muse.lastSeen.model/-last-seen-entry/index.md) related tasks.

## Constructors

| | |
|---|---|
| [LastSeenService](-last-seen-service.md) | [jvm]<br>fun [LastSeenService](-last-seen-service.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowiredlastSeenRepository: [LastSeenRepository](../../org.darkSolace.muse.lastSeen.repository/-last-seen-repository/index.md), @Value(value = &quot;${session.timeInMinutes}&quot;)sessionTimeInMinutes: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Functions

| Name | Summary |
|---|---|
| [getBySession](get-by-session.md) | [jvm]<br>fun [getBySession](get-by-session.md)(sessionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [LastSeenEntry](../../org.darkSolace.muse.lastSeen.model/-last-seen-entry/index.md)?<br>Retrieves a [LastSeenEntry](../../org.darkSolace.muse.lastSeen.model/-last-seen-entry/index.md) for a given session ID |
| [getByUsername](get-by-username.md) | [jvm]<br>fun [getByUsername](get-by-username.md)(userName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LastSeenEntry](../../org.darkSolace.muse.lastSeen.model/-last-seen-entry/index.md)?<br>Retrieves a [LastSeenEntry](../../org.darkSolace.muse.lastSeen.model/-last-seen-entry/index.md) for a given [User](../../org.darkSolace.muse.user.model/-user/index.md), identified by its username |
| [getOnlineUserCount](get-online-user-count.md) | [jvm]<br>fun [getOnlineUserCount](get-online-user-count.md)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>Returns the number of currently active visitors (logged-in and not) |
| [getOnlineUsers](get-online-users.md) | [jvm]<br>fun [getOnlineUsers](get-online-users.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;<br>Returns a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of currently active, logged-in users |
| [prune](prune.md) | [jvm]<br>@Scheduled(cron = &quot;0/30 * * * * *&quot;)<br>@Transactional<br>fun [prune](prune.md)()<br>Removes timed-out [LastSeenEntry](../../org.darkSolace.muse.lastSeen.model/-last-seen-entry/index.md)s from the database This method is executed every 30 seconds |
| [updateLastSeen](update-last-seen.md) | [jvm]<br>fun [updateLastSeen](update-last-seen.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, jSession: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Updates a [LastSeenEntry](../../org.darkSolace.muse.lastSeen.model/-last-seen-entry/index.md), identified either by username (if the user is logged-in) or by session ID |

## Properties

| Name | Summary |
|---|---|
| [lastSeenRepository](last-seen-repository.md) | [jvm]<br>val [lastSeenRepository](last-seen-repository.md): [LastSeenRepository](../../org.darkSolace.muse.lastSeen.repository/-last-seen-repository/index.md) |
| [sessionTimeInMinutes](session-time-in-minutes.md) | [jvm]<br>val [sessionTimeInMinutes](session-time-in-minutes.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md) |
