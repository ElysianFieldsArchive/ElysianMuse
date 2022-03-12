//[elysianmuse](../../index.md)/[org.darkSolace.muse.statisticsModule.service](index.md)

# Package org.darkSolace.muse.statisticsModule.service

## Types

| Name | Summary |
|---|---|
| [LastSeenFilter](-last-seen-filter/index.md) | [jvm]<br>@Component<br>class [LastSeenFilter](-last-seen-filter/index.md) : OncePerRequestFilter<br>Filter to create or update [org.darkSolace.muse.statisticsModule.model.LastSeenEntry](../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md) for each request |
| [LastSeenService](-last-seen-service/index.md) | [jvm]<br>@Service<br>class [LastSeenService](-last-seen-service/index.md)(@AutowireduserRepository: [UserRepository](../org.darkSolace.muse.userModule.repository/-user-repository/index.md), @AutowiredlastSeenRepository: [LastSeenRepository](../org.darkSolace.muse.statisticsModule.repository/-last-seen-repository/index.md), @Value(value = "${session.timeInMinutes}")sessionTimeInMinutes: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Service class for [LastSeenEntry](../org.darkSolace.muse.statisticsModule.model/-last-seen-entry/index.md) related tasks. |
