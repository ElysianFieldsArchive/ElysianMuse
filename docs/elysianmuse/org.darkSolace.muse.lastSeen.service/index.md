//[elysianmuse](../../index.md)/[org.darkSolace.muse.lastSeen.service](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [LastSeenFilter](-last-seen-filter/index.md) | [jvm]<br>@Component<br>class [LastSeenFilter](-last-seen-filter/index.md) : OncePerRequestFilter<br>Filter to create or update [org.darkSolace.muse.lastSeen.model.LastSeenEntry](../org.darkSolace.muse.lastSeen.model/-last-seen-entry/index.md) for each request |
| [LastSeenService](-last-seen-service/index.md) | [jvm]<br>@Service<br>class [LastSeenService](-last-seen-service/index.md)(@Autowiredval userRepository: [UserRepository](../org.darkSolace.muse.user.repository/-user-repository/index.md), @Autowiredval lastSeenRepository: [LastSeenRepository](../org.darkSolace.muse.lastSeen.repository/-last-seen-repository/index.md), @Value(value = &quot;${session.timeInMinutes}&quot;)val sessionTimeInMinutes: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Service class for [LastSeenEntry](../org.darkSolace.muse.lastSeen.model/-last-seen-entry/index.md) related tasks. |
