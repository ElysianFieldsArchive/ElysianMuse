//[elysianmuse](../../index.md)/[org.darkSolace.muse.statistics.service](index.md)

# Package org.darkSolace.muse.statistics.service

## Types

| Name | Summary |
|---|---|
| [LastSeenFilter](-last-seen-filter/index.md) | [jvm]<br>@Component<br>class [LastSeenFilter](-last-seen-filter/index.md) : OncePerRequestFilter<br>Filter to create or update [org.darkSolace.muse.statistics.model.LastSeenEntry](../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md) for each request |
| [LastSeenService](-last-seen-service/index.md) | [jvm]<br>@Service<br>class [LastSeenService](-last-seen-service/index.md)(@AutowireduserRepository: [UserRepository](../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowiredlastSeenRepository: [LastSeenRepository](../org.darkSolace.muse.statistics.repository/-last-seen-repository/index.md), @Value(value = "${session.timeInMinutes}")sessionTimeInMinutes: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Service class for [LastSeenEntry](../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md) related tasks. |
