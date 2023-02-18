//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.service](../index.md)/[NewsService](index.md)/[createNews](create-news.md)

# createNews

[jvm]\
fun [createNews](create-news.md)(
newsDto: [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Creates a new [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md).

#### Return

*true* if the [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md) was created successfully, *false*
in case of an invalid [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md).

#### Parameters

jvm

|         |                                                                                                                                                                                                          |
|---------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| newsDto | a [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md) containing all the details of the [NewsEntry](../../org.darkSolace.muse.news.model/-news-entry/index.md) to create |
