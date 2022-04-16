//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.service](../index.md)/[UserService](index.md)/[markEMailAsValid](mark-e-mail-as-valid.md)

# markEMailAsValid

[jvm]\
fun [markEMailAsValid](mark-e-mail-as-valid.md)(user: [User](../../org.darkSolace.muse.user.model/-user/index.md))

Marks the email of the given user as validated and cleans up all data regarding the (previously outstanding) validation

TODO: Clean up validation

## Parameters

jvm

| | |
|---|---|
| user | the [User](../../org.darkSolace.muse.user.model/-user/index.md) to update |

[jvm]\
fun [markEMailAsValid](mark-e-mail-as-valid.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Marks the email of the given user, identified by its username, as validated and cleans up all data regarding the (previously outstanding) validation.

## Parameters

jvm

| | |
|---|---|
| username | the username of the [User](../../org.darkSolace.muse.user.model/-user/index.md) to update |
