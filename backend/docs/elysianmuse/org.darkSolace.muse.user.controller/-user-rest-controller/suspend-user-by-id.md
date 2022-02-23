//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserRestController](index.md)
/[suspendUserById](suspend-user-by-id.md)

# suspendUserById

[jvm]\

@PostMapping(value = ["/suspend/{user}"])

@PreAuthorize(value = "hasAnyAuthority('ADMINISTRATOR', 'MODERATOR')")

fun [suspendUserById](suspend-user-by-id.md)(
@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)?):
ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Suspends a user identified by its id. Listens on /api/user/{id} for POST requests. You need
the [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md)
or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md)
role to access this endpoint.

#### Return

HTTP 200 or 400

## Samples

## Parameters

jvm

| | |
|---|---|
| user | the user id |
