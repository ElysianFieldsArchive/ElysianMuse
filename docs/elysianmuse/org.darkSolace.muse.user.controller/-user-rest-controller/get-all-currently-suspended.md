//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserRestController](index.md)/[getAllCurrentlySuspended](get-all-currently-suspended.md)

# getAllCurrentlySuspended

[jvm]\

@GetMapping(value = ["/suspend/all"])

@PreAuthorize(value = "hasAnyAuthority('ADMINISTRATION', 'MODERATOR')")

fun [getAllCurrentlySuspended](get-all-currently-suspended.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;

Retrieves all currently suspended users You need the [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) role to access this endpoint.

#### Return

List of the suspended [User](../../org.darkSolace.muse.user.model/-user/index.md)s

## Samples
