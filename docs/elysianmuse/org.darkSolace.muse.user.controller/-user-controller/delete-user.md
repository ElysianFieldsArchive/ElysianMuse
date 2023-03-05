//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserController](index.md)/[deleteUser](delete-user.md)

# deleteUser

[jvm]\

@DeleteMapping(value = [&quot;/{user}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [deleteUser](delete-user.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)?, authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Deletes a user identified by its id. Listens on /api/user/{id} for DELETE requests. You need the [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) role to access this endpoint.

#### Return

HTTP 200 on success, HTTP 401 otherwise

#### Parameters

jvm

| | |
|---|---|
| user | the user id |

#### Samples
