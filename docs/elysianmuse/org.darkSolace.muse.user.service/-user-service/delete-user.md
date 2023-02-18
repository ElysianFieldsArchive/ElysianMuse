//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[UserService](index.md)/[deleteUser](delete-user.md)

# deleteUser

[jvm]\

@Transactional

fun [deleteUser](delete-user.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md))

Deletes a [User](../../org.darkSolace.muse.user.model/-user/index.md) from the database

## Parameters

jvm

| | |
|---|---|
| user | the [User](../../org.darkSolace.muse.user.model/-user/index.md) to be deleted<br>TODO: deal with created content by this user<br>-     assign stories and chapters to orphan accounts -     replace username in private messages and comments -     other occurrences where a user might be involved |

[jvm]\

@Transactional

fun [deleteUser](delete-user.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))

Deletes a [User](../../org.darkSolace.muse.user.model/-user/index.md) from the database

## Parameters

jvm

| | |
|---|---|
| id | of the [User](../../org.darkSolace.muse.user.model/-user/index.md) to be deleted |
