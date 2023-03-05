//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[SuspensionService](index.md)

# SuspensionService

[jvm]\
@Service

class [SuspensionService](index.md)(@Autowiredval userRoleService: [UserRoleService](../-user-role-service/index.md), @Autowiredval userRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @Autowiredval suspensionHistoryRepository: [SuspensionHistoryRepository](../../org.darkSolace.muse.user.repository/-suspension-history-repository/index.md))

## Constructors

| | |
|---|---|
| [SuspensionService](-suspension-service.md) | [jvm]<br>constructor(@AutowireduserRoleService: [UserRoleService](../-user-role-service/index.md), @AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md), @AutowiredsuspensionHistoryRepository: [SuspensionHistoryRepository](../../org.darkSolace.muse.user.repository/-suspension-history-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [confirmSuspension](confirm-suspension.md) | [jvm]<br>fun [confirmSuspension](confirm-suspension.md)(confirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Confirms the acceptance of a [SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md), identified by its confirmation code. |
| [getAllCurrentlySuspendedUsers](get-all-currently-suspended-users.md) | [jvm]<br>fun [getAllCurrentlySuspendedUsers](get-all-currently-suspended-users.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;<br>Retrieves all currently suspended users |
| [getSuspensionHistory](get-suspension-history.md) | [jvm]<br>fun [getSuspensionHistory](get-suspension-history.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md)&gt;<br>Retrieves the suspension history of a given user, identified by its id.<br>[jvm]<br>fun [getSuspensionHistory](get-suspension-history.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md)&gt;<br>Retrieves the suspension history of a given user. |
| [suspensionCodeForUsername](suspension-code-for-username.md) | [jvm]<br>fun [suspensionCodeForUsername](suspension-code-for-username.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Retrieves the confirmation code for a users open [SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md). The user is identified by its username. |

## Properties

| Name | Summary |
|---|---|
| [suspensionHistoryRepository](suspension-history-repository.md) | [jvm]<br>val [suspensionHistoryRepository](suspension-history-repository.md): [SuspensionHistoryRepository](../../org.darkSolace.muse.user.repository/-suspension-history-repository/index.md) |
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md) |
| [userRoleService](user-role-service.md) | [jvm]<br>val [userRoleService](user-role-service.md): [UserRoleService](../-user-role-service/index.md) |
