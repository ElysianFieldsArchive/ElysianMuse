//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.service](../index.md)/[NewsService](index.md)/[editNews](edit-news.md)

# editNews

[jvm]\
fun [editNews](edit-news.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html),
newsDto: [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Edits an existing [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md), new values are specified by
an [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md)

#### Return

*true* if the [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md) was edited successfully, *false* in
case of an error

#### Parameters

jvm

|         |                                                                                                                   |
|---------|-------------------------------------------------------------------------------------------------------------------|
| id      | the id of the [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md) to be edited                 |
| newsDto | [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md) containing the edited details |
