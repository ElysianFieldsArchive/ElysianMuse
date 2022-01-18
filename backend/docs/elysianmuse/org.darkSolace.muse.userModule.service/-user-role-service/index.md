//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.service](../index.md)/[UserRoleService](index.md)

# UserRoleService

[jvm]\
@Service

class [UserRoleService](index.md)(
@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md),
@AutowiredsuspensionHistoryRepository: [SuspensionHistoryRepository](../../org.darkSolace.muse.userModule.repository/-suspension-history-repository/index.md))

Service class for [Role](../../org.darkSolace.muse.userModule.model/-role/index.md) related tasks when working
with [User](../../org.darkSolace.muse.userModule.model/-user/index.md)s.

## See also

jvm

| | |
|---|---|
| [org.darkSolace.muse.userModule.service.UserService](../-user-service/index.md) |  |

## Constructors

| | |
|---|---|
| [UserRoleService](-user-role-service.md) | [jvm]<br>fun [UserRoleService](-user-role-service.md)(@AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md), @AutowiredsuspensionHistoryRepository: [SuspensionHistoryRepository](../../org.darkSolace.muse.userModule.repository/-suspension-history-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [changeRole](change-role.md) | [jvm]<br>@Transactional<br>fun [changeRole](change-role.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md), role: [Role](../../org.darkSolace.muse.userModule.model/-role/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?<br>Changes the role of a [User](../../org.darkSolace.muse.userModule.model/-user/index.md) and persists it in the database. |
| [confirmSuspension](confirm-suspension.md) | [jvm]<br>fun [confirmSuspension](confirm-suspension.md)(confirmationCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Confirms the acceptance of a [SuspensionHistoryEntry](../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md), identified by its confirmation code. |
| [getSuspensionHistory](get-suspension-history.md) | [jvm]<br>fun [getSuspensionHistory](get-suspension-history.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SuspensionHistoryEntry](../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md)&gt;<br>Retrieves the suspention history of a given user, identified by its id.<br>[jvm]<br>fun [getSuspensionHistory](get-suspension-history.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SuspensionHistoryEntry](../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md)&gt;<br>Retrieves the suspention history of a given user. |
| [suspendUser](suspend-user.md) | [jvm]<br>@Transactional<br>fun [suspendUser](suspend-user.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?<br>Suspends the provided [User](../../org.darkSolace.muse.userModule.model/-user/index.md), identified by its id, and persists it in the database. Also creates a [SuspensionHistoryEntry](../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md) if it doesn't exist.<br>[jvm]<br>@Transactional<br>fun [suspendUser](suspend-user.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md)): [User](../../org.darkSolace.muse.userModule.model/-user/index.md)?<br>Suspends the provided [User](../../org.darkSolace.muse.userModule.model/-user/index.md) and persists it in the database. |
| [suspensionCodeForUsername](suspension-code-for-username.md) | [jvm]<br>fun [suspensionCodeForUsername](suspension-code-for-username.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Retrieves the confirmation code for a users open [SuspensionHistoryEntry](../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md). The user is identified by its username. |

## Properties

| Name | Summary |
|---|---|
| [suspensionHistoryRepository](suspension-history-repository.md) | [jvm]<br>val [suspensionHistoryRepository](suspension-history-repository.md): [SuspensionHistoryRepository](../../org.darkSolace.muse.userModule.repository/-suspension-history-repository/index.md) |
| [userRepository](user-repository.md) | [jvm]<br>val [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.userModule.repository/-user-repository/index.md) |
