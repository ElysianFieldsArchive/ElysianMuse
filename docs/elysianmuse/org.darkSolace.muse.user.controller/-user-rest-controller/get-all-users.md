//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserRestController](index.md)/[getAllUsers](get-all-users.md)

# getAllUsers

[jvm]\

@GetMapping(value = [&quot;/all&quot;])

fun [getAllUsers](get-all-users.md)(authentication: Authentication?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;

Retrieves all users. Listens on /api/user/all.

#### Return

a List of [User](../../org.darkSolace.muse.user.model/-user/index.md)s - might be empty

#### Samples
