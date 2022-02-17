//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserRestController](index.md)/[getSuspensionHistory](get-suspension-history.md)

# getSuspensionHistory

[jvm]\

@GetMapping(value = ["/suspend/history/{user}"])

@PreAuthorize(value = "hasAnyAuthority('ADMINISTRATION', 'MODERATOR')")

fun [getSuspensionHistory](get-suspension-history.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md)&gt;

Retrieves the suspension history for a given user, identified by its id. You need the [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) role to access this endpoint.

#### Return

List of [SuspensionHistoryEntry](../../org.darkSolace.muse.user.model/-suspension-history-entry/index.md)s

## Samples

## Parameters

jvm

| | |
|---|---|
| user | the user id |
