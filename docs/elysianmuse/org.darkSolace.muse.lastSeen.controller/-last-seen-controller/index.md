//[elysianmuse](../../../index.md)/[org.darkSolace.muse.lastSeen.controller](../index.md)/[LastSeenController](index.md)

# LastSeenController

[jvm]\
@RestController

@RequestMapping(value = [&quot;api/lastSeen&quot;])

class [LastSeenController](index.md)(@Autowiredval lastSeenService: [LastSeenService](../../org.darkSolace.muse.lastSeen.service/-last-seen-service/index.md))

## Constructors

| | |
|---|---|
| [LastSeenController](-last-seen-controller.md) | [jvm]<br>fun [LastSeenController](-last-seen-controller.md)(@AutowiredlastSeenService: [LastSeenService](../../org.darkSolace.muse.lastSeen.service/-last-seen-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getOnlineUsers](get-online-users.md) | [jvm]<br>@GetMapping(value = [&quot;/online&quot;])<br>fun [getOnlineUsers](get-online-users.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;<br>Retrieves all public profiles of currently active logged-in [User](../../org.darkSolace.muse.user.model/-user/index.md)s |
| [getOnlineUsersCount](get-online-users-count.md) | [jvm]<br>@GetMapping(value = [&quot;/online/count&quot;])<br>fun [getOnlineUsersCount](get-online-users-count.md)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>Retrieves number of currently active visitors (logged-in users and visitors) |

## Properties

| Name | Summary |
|---|---|
| [lastSeenService](last-seen-service.md) | [jvm]<br>val [lastSeenService](last-seen-service.md): [LastSeenService](../../org.darkSolace.muse.lastSeen.service/-last-seen-service/index.md) |
