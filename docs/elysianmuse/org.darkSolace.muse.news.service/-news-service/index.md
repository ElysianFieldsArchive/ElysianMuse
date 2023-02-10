//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.service](../index.md)/[NewsService](index.md)

# NewsService

[jvm]\
@Service

class [NewsService](index.md)

Service class for [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md)
and [NewsComment](../../org.darkSolace.muse.news.model/-news-comment/index.md) related tasks.

## Constructors

|                                 |                                                |
|---------------------------------|------------------------------------------------|
| [NewsService](-news-service.md) | [jvm]<br>fun [NewsService](-news-service.md)() |

## Functions

| Name                                       | Summary                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
|--------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [addCommentToNews](add-comment-to-news.md) | [jvm]<br>fun [addCommentToNews](add-comment-to-news.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), comment: [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Adds a new [NewsComment](../../org.darkSolace.muse.news.model/-news-comment/index.md) to an existing [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md).                                       |
| [createNews](create-news.md)               | [jvm]<br>fun [createNews](create-news.md)(newsDto: [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Creates a new [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md).                                                                                                                                                                                                                                  |
| [editComment](edit-comment.md)             | [jvm]<br>fun [editComment](edit-comment.md)(commentId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), commentDto: [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Edits an existing [NewsComment](../../org.darkSolace.muse.news.model/-news-comment/index.md), new values are specified by an [NewsCommentDTO](../../org.darkSolace.muse.news.model.dto/-news-comment-d-t-o/index.md) |
| [editNews](edit-news.md)                   | [jvm]<br>fun [editNews](edit-news.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), newsDto: [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Edits an existing [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md), new values are specified by an [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md)                             |
| [getAllNews](get-all-news.md)              | [jvm]<br>fun [getAllNews](get-all-news.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md)&gt;<br>Returns all [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md)s                                                                                                                                                                                                                                           |
| [getLast](get-last.md)                     | [jvm]<br>fun [getLast](get-last.md)(amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md)&gt;<br>Returns the last (newest) *amount*[NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md)s.                                                                                                                                         |
| [getNews](get-news.md)                     | [jvm]<br>fun [getNews](get-news.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md)?<br>Returns a [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md) identified by its id.                                                                                                                                                                                                                                              |

## Properties

| Name                                                | Summary                                                                                                                                                                                       |
|-----------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [newsCommentRepository](news-comment-repository.md) | [jvm]<br>@Autowired<br>lateinit var [newsCommentRepository](news-comment-repository.md): [NewsCommentRepository](../../org.darkSolace.muse.news.repository/-news-comment-repository/index.md) |
| [newsRepository](news-repository.md)                | [jvm]<br>@Autowired<br>lateinit var [newsRepository](news-repository.md): [NewsRepository](../../org.darkSolace.muse.news.repository/-news-repository/index.md)                               |
| [userRepository](user-repository.md)                | [jvm]<br>@Autowired<br>lateinit var [userRepository](user-repository.md): [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md)                               |
