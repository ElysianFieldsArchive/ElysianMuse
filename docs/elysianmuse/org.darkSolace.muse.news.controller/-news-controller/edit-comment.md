//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.controller](../index.md)/[NewsController](index.md)/[editComment](edit-comment.md)

# editComment

[jvm]\

@PostMapping(value = [&quot;/comment/{commentId}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [editComment](edit-comment.md)(@PathVariablecommentId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestBodycommentDto: [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Edits a comment on a news entry. To edit a comment the [org.darkSolace.muse.user.model.Role](../../org.darkSolace.muse.user.model/-role/index.md) of [org.darkSolace.muse.user.model.Role.MEMBER](../../org.darkSolace.muse.user.model/-role/-m-e-m-b-e-r/index.md), [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) is required.

#### Parameters

jvm

| | |
|---|---|
| commentId | the id of the comment to be edited |
| commentDto | the [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md) containing the edited comment |

#### Samples
