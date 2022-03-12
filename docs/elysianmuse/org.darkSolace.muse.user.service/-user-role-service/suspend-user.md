//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[UserRoleService](index.md)/[suspendUser](suspend-user.md)

# suspendUser

[jvm]\

@Transactional

fun [suspendUser](suspend-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?

Suspends the provided [User](../../org.darkSolace.muse.user.model/-user/index.md) and persists it in the database.

#### Return

the suspended [User](../../org.darkSolace.muse.user.model/-user/index.md) or null if the [User](../../org.darkSolace.muse.user.model/-user/index.md) was not found

## Parameters

jvm

| | |
|---|---|
| user | the [User](../../org.darkSolace.muse.user.model/-user/index.md) to be suspended |

[jvm]\

@Transactional

fun [suspendUser](suspend-user.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [User](../../org.darkSolace.muse.user.model/-user/index.md)?

Suspends the provided [User](../../org.darkSolace.muse.user.model/-user/index.md), identified by its id, and persists it in the database. Also creates a [SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md) if it doesn't exist.

#### Return

the suspended [User](../../org.darkSolace.muse.user.model/-user/index.md) or null if the [User](../../org.darkSolace.muse.user.model/-user/index.md) was not found

## Parameters

jvm

| | |
|---|---|
| id | of the [User](../../org.darkSolace.muse.user.model/-user/index.md) to be suspended |
