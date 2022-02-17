//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.service](../index.md)/[UserService](index.md)/[deleteUser](delete-user.md)

# deleteUser

[jvm]\

@Transactional

fun [deleteUser](delete-user.md)(user: [User](../../org.darkSolace.muse.userModule.model/-user/index.md))

Deletes a [User](../../org.darkSolace.muse.userModule.model/-user/index.md) from the database

## Parameters

jvm

| | |
|---|---|
| user | the [User](../../org.darkSolace.muse.userModule.model/-user/index.md) to be deleted<br>TODO: deal with created content by this user<br><ul><li>assign stories and chapters to orphan accounts</li><li>replace username in private messages and comments</li><li>other occurrences where a user might be involved</li></ul> |

[jvm]\

@Transactional

fun [deleteUser](delete-user.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))

Deletes a [User](../../org.darkSolace.muse.userModule.model/-user/index.md) from the database

## Parameters

jvm

| | |
|---|---|
| id | of the [User](../../org.darkSolace.muse.userModule.model/-user/index.md) to be deleted |
