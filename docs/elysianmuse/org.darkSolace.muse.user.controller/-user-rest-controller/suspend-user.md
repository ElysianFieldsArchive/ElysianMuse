//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserRestController](index.md)/[suspendUser](suspend-user.md)

# suspendUser

[jvm]\

@PostMapping(value = [&quot;/suspend/{id}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('ADMINISTRATOR', 'MODERATOR')&quot;)

fun [suspendUser](suspend-user.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Suspends a user identified by its id. Listens on /api/user/{id} for POST requests. You need the [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) role to access this endpoint.

#### Return

HTTP 200 or 400

## Samples

## Parameters

jvm

| | |
|---|---|
| id | the user id |
