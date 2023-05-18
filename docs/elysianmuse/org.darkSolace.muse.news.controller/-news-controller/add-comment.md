//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.controller](../index.md)/[NewsController](index.md)/[addComment](add-comment.md)

# addComment

[jvm]\

@PutMapping(value = [&quot;/{id}/comment&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [addComment](add-comment.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestBody@ValidcommentDto: [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Adds a comment to a news entry. To add a comment the [org.darkSolace.muse.user.model.Role](../../org.darkSolace.muse.user.model/-role/index.md) of [org.darkSolace.muse.user.model.Role.MEMBER](../../org.darkSolace.muse.user.model/-role/-m-e-m-b-e-r/index.md), [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) is required.

#### Parameters

jvm

| | |
|---|---|
| id | the id of the [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md) to be edited |
| commentDto | the [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md) containing the comment to be added |

#### Samples
