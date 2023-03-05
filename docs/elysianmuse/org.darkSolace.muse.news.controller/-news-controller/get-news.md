//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.controller](../index.md)/[NewsController](index.md)/[getNews](get-news.md)

# getNews

[jvm]\

@GetMapping(value = [&quot;/{id}&quot;])

fun [getNews](get-news.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): ResponseEntity&lt;*&gt;

Retrieves a [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md) by its id. Listens on /api/news/{id}.

#### Return

the retrieved [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md) or `null`

#### Parameters

jvm

| | |
|---|---|
| id | the news id |

#### Samples
