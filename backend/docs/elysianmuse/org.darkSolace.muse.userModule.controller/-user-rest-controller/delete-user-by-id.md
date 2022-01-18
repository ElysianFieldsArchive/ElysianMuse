//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.controller](../index.md)
/[UserRestController](index.md)/[deleteUserById](delete-user-by-id.md)

# deleteUserById

[jvm]\

@DeleteMapping(value = ["/{id}"])

@PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")

fun [deleteUserById](delete-user-by-id.md)(
@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))

Deletes a user identified by its id. Listens on /api/user/{id} for DELETE requests. You need
the [org.darkSolace.muse.userModule.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.userModule.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md)
role to access this endpoint.

## Samples

## Parameters

jvm

| | |
|---|---|
| id | the user id |
