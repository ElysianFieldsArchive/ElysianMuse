//[elysianmuse](../../../index.md)/[org.darkSolace.muse.statisticsModule.controller](../index.md)
/[StatisticsController](index.md)

# StatisticsController

[jvm]\
@RestController

@RequestMapping(value = ["api/stats"])

class [StatisticsController](index.md)(
@AutowiredlastSeenService: [LastSeenService](../../org.darkSolace.muse.statisticsModule.service/-last-seen-service/index.md))

## Functions

| Name | Summary |
|---|---|
| [getOnlineUsers](get-online-users.md) | [jvm]<br>@GetMapping(value = ["/online"])<br>fun [getOnlineUsers](get-online-users.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.userModule.model/-user/index.md)&gt;<br>Retrieves all public profiles of currently active logged-in [User](../../org.darkSolace.muse.userModule.model/-user/index.md)s |
| [getOnlineUsersCount](get-online-users-count.md) | [jvm]<br>@GetMapping(value = ["/online/count"])<br>fun [getOnlineUsersCount](get-online-users-count.md)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>Retrieves number of currently active visitors (logged-in users and visitors) |

## Properties

| Name | Summary |
|---|---|
| [lastSeenService](last-seen-service.md) | [jvm]<br>val [lastSeenService](last-seen-service.md): [LastSeenService](../../org.darkSolace.muse.statisticsModule.service/-last-seen-service/index.md) |
