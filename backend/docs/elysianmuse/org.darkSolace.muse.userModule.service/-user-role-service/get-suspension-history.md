//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.service](../index.md)/[UserRoleService](index.md)
/[getSuspensionHistory](get-suspension-history.md)

# getSuspensionHistory

[jvm]\
fun [getSuspensionHistory](get-suspension-history.md)(
id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SuspensionHistoryEntry](
../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md)&gt;

Retrieves the suspension history of a given user, identified by its id.

#### Return

List of [SuspensionHistoryEntry](../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md)s

## Parameters

jvm

| | |
|---|---|
| id | id of the user |

[jvm]\
fun [getSuspensionHistory](get-suspension-history.md)(
user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SuspensionHistoryEntry](
../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md)&gt;

Retrieves the suspension history of a given user.

#### Return

List of [SuspensionHistoryEntry](../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md)s

## Parameters

jvm

| | |
|---|---|
| user | the user |
