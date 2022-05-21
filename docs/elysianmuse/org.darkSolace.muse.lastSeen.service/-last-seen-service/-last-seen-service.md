//[elysianmuse](../../../index.md)/[org.darkSolace.muse.lastSeen.service](../index.md)/[LastSeenService](index.md)/[LastSeenService](-last-seen-service.md)

# LastSeenService

[jvm]\
fun [LastSeenService](-last-seen-service.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowiredlastSeenRepository: [LastSeenRepository](../../org.darkSolace.muse.lastSeen.repository/-last-seen-repository/index.md), @Value(value = &quot;${session.timeInMinutes}&quot;)sessionTimeInMinutes: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))
