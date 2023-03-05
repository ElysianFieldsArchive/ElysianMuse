//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserController](index.md)/[removeTagFromUser](remove-tag-from-user.md)

# removeTagFromUser

[jvm]\

@DeleteMapping(value = [&quot;/{user}/tag/{tag}&quot;])

fun [removeTagFromUser](remove-tag-from-user.md)(@PathVariableuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)?, @PathVariabletag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Removes a [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) from a [User](../../org.darkSolace.muse.user.model/-user/index.md). A [User](../../org.darkSolace.muse.user.model/-user/index.md) can remove [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)s from itself. To remove tags from a different user either the [org.darkSolace.muse.user.model.Role](../../org.darkSolace.muse.user.model/-role/index.md) of [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) is required.

#### Parameters

jvm

| | |
|---|---|
| user | the id of the [User](../../org.darkSolace.muse.user.model/-user/index.md) to remove a [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) from |
| tag | the [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) to remove |

#### Samples
