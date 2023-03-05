//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.service](../index.md)/[NewsService](index.md)/[addCommentToNews](add-comment-to-news.md)

# addCommentToNews

[jvm]\
fun [addCommentToNews](add-comment-to-news.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), comment: [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Adds a new [NewsComment](../../org.darkSolace.muse.news.model/-news-comment/index.md) to an existing [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md).

#### Return

*true* if the comment was added successfully, *false* in case of an error

#### Parameters

jvm

| | |
|---|---|
| id | Id of the [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md) to add the comment to |
| comment | A [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md) containing the details of the comment |
