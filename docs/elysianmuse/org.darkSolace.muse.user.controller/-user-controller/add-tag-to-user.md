//[elysianmuse](../../../index.md)/[org.darkSolace.muse.user.controller](../index.md)/[UserController](index.md)/[addTagToUser](add-tag-to-user.md)

# addTagToUser

[jvm]\

@PutMapping(value = [&quot;/{user}/tag/{tag}&quot;])

fun [addTagToUser](add-tag-to-user.md)(@PathVariable@Validuser: [User](../../org.darkSolace.muse.user.model/-user/index.md)?, @PathVariable@Validtag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Adds a [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) to a [User](../../org.darkSolace.muse.user.model/-user/index.md). A [User](../../org.darkSolace.muse.user.model/-user/index.md) can add [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)s to itself. To add tags to a different user either the [org.darkSolace.muse.user.model.Role](../../org.darkSolace.muse.user.model/-role/index.md) of [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) is required.

#### Parameters

jvm

| | |
|---|---|
| user | the id of the [User](../../org.darkSolace.muse.user.model/-user/index.md) to add a [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) to |
| tag | the [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md) to add |

#### Samples
