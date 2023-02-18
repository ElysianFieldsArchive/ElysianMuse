//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.controller](../index.md)/[NewsController](index.md)/[deleteNews](delete-news.md)

# deleteNews

[jvm]\

@DeleteMapping(value = [&quot;/{newsId}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('ADMINISTRATOR')&quot;)

fun [deleteNews](delete-news.md)(
@PathVariablenewsId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), authentication:
Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Deletes the specified NewsEntry. Can only be called by a user with
role [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md)

#### Parameters

jvm

|        |                                   |
|--------|-----------------------------------|
| newsId | id of the NewsEntry to be deleted |
