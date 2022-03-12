//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.controller](../index.md)/[UserRestController](index.md)/[suspendUserById](suspend-user-by-id.md)

# suspendUserById

[jvm]\

@PostMapping(value = ["/suspend/{id}"])

@PreAuthorize(value = "hasAnyAuthority('ADMINISTRATOR', 'MODERATOR')")

fun [suspendUserById](suspend-user-by-id.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): ResponseEntity&lt;*&gt;

Suspends a user identified by its id. Listens on /api/user/{id} for POST requests. You need the [org.darkSolace.muse.userModule.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.userModule.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.userModule.model.Role.MODERATOR](../../org.darkSolace.muse.userModule.model/-role/-m-o-d-e-r-a-t-o-r/index.md) role to access this endpoint.

#### Return

HTTP 200 or 400

## Samples

## Parameters

jvm

| | |
|---|---|
| id | the user id |
