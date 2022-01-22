//[elysianmuse](../../../index.md)/[org.darkSolace.muse.userModule.controller](../index.md)
/[UserRestController](index.md)/[getSuspensionHistory](get-suspension-history.md)

# getSuspensionHistory

[jvm]\

@GetMapping(value = ["/suspend/history/{id}"])

@PreAuthorize(value = "hasAnyAuthority('ADMINISTRATION', 'MODERATOR')")

fun [getSuspensionHistory](get-suspension-history.md)(
@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SuspensionHistoryEntry](
../../org.darkSolace.muse.userModule.model/-suspension-history-entry/index.md)&gt;

Retrieves the suspension history for a given user, identified by its id. You need
the [org.darkSolace.muse.userModule.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.userModule.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md)
or [org.darkSolace.muse.userModule.model.Role.MODERATOR](../../org.darkSolace.muse.userModule.model/-role/-m-o-d-e-r-a-t-o-r/index.md)
role to access this endpoint.

## Samples

## Parameters

jvm

| | |
|---|---|
| id | the user id |
