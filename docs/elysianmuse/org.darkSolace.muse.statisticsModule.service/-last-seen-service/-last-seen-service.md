//[elysianmuse](../../../index.md)/[org.darkSolace.muse.statisticsModule.service](../index.md)/[LastSeenService](index.md)/[LastSeenService](-last-seen-service.md)

# LastSeenService

[jvm]\
fun [LastSeenService](-last-seen-service.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md), @AutowiredlastSeenRepository: [LastSeenRepository](../../org.darkSolace.muse.statisticsModule.repository/-last-seen-repository/index.md), @Value(value = "${session.timeInMinutes}")sessionTimeInMinutes: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))
