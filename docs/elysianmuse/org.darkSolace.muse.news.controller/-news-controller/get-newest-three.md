//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.controller](../index.md)/[NewsController](index.md)/[getNewestThree](get-newest-three.md)

# getNewestThree

[jvm]\

@GetMapping(value = [&quot;/last&quot;])

fun [getNewestThree](get-newest-three.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)
&lt;[NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md)&gt;

Retrieves the last 3 news stories. Listens on /api/news/last.

#### Return

List of [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md)s. Max size 3.

#### Samples
