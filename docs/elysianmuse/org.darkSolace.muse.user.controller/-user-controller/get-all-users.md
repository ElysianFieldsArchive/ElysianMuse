//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserController](index.md)/[getAllUsers](get-all-users.md)

# getAllUsers

[jvm]\

@GetMapping(value = [&quot;/all&quot;])

fun [getAllUsers](get-all-users.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserIdNameDTO](../../org.darkSolace.muse.user.model.dto/-user-id-name-d-t-o/index.md)&gt;

Retrieves all users. Listens on /api/user/all.

#### Return

a List of [UserIdNameDTO](../../org.darkSolace.muse.user.model.dto/-user-id-name-d-t-o/index.md)s (username and id) - might be empty

#### Samples
