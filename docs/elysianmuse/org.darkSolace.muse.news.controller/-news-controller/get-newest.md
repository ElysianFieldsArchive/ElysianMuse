//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.controller](../index.md)/[NewsController](index.md)/[getNewest](get-newest.md)

# getNewest

[jvm]\

@GetMapping(value = [&quot;/last/{size}&quot;])

fun [getNewest](get-newest.md)(@Valid@PathVariable(required = false)@Min(value = 1)
size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) =
3): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)
&lt;[NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md)&gt;

Retrieves the last 'size' news stories. Listens on /api/news/last/{size}.

#### Return

List of [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md)s. Defaults to size 3.

#### Samples

#### Parameters

jvm

|      |                                                    |
|------|----------------------------------------------------|
| size | Number of news entries to retrieve. Defaults to 3. |
