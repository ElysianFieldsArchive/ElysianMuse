//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.controller](../index.md)/[NewsController](index.md)/[deleteComment](delete-comment.md)

# deleteComment

[jvm]\

@DeleteMapping(value = [&quot;/comment/{commentId}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [deleteComment](delete-comment.md)(
@PathVariablecommentId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), authentication:
Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Deletes the specified [NewsComment](../../org.darkSolace.muse.news.model/-news-comment/index.md). Can be called by a
user with
role [Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md), [Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md)
or the author itself.

#### Parameters

jvm

|           |                                                                                                    |
|-----------|----------------------------------------------------------------------------------------------------|
| commentId | id of the [NewsComment](../../org.darkSolace.muse.news.model/-news-comment/index.md) to be deleted |
