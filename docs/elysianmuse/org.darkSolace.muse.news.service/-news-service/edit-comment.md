//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.service](../index.md)/[NewsService](index.md)/[editComment](edit-comment.md)

# editComment

[jvm]\
fun [editComment](edit-comment.md)(commentId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), commentDto: [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Edits an existing [NewsComment](../../org.darkSolace.muse.news.model/-news-comment/index.md), new values are specified by an [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md)

#### Return

*true* if the [NewsComment](../../org.darkSolace.muse.news.model/-news-comment/index.md) was edited successfully, *false* in case of an error

#### Parameters

jvm

| | |
|---|---|
| commentId | the id of the [NewsComment](../../org.darkSolace.muse.news.model/-news-comment/index.md) to be edited |
| commentDto | [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md) containing the edited details |
