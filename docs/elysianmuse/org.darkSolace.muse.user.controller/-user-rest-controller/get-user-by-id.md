//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserRestController](index.md)/[getUserById](get-user-by-id.md)

# getUserById

[jvm]\

@GetMapping(value = ["/{id}"])

fun [getUserById](get-user-by-id.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), authentication: Authentication?): [User](../../org.darkSolace.muse.user.model/-user/index.md)?

Retrieves a user by its id. Listens on /api/user/{id}.

#### Return

the retrieved [User](../../org.darkSolace.muse.user.model/-user/index.md) or null

## Samples

## Parameters

jvm

| | |
|---|---|
| id | the user id |
