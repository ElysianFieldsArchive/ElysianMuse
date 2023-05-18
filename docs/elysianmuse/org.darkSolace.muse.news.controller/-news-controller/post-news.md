//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.controller](../index.md)/[NewsController](index.md)/[postNews](post-news.md)

# postNews

[jvm]\

@PutMapping

@PreAuthorize(value = &quot;hasAnyAuthority('MODERATOR', 'ADMINISTRATOR')&quot;)

fun [postNews](post-news.md)(@RequestBody@Validnews: [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Adds a news entry. To add news the [org.darkSolace.muse.user.model.Role](../../org.darkSolace.muse.user.model/-role/index.md) of [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) is required.

#### Parameters

jvm

| | |
|---|---|
| news | the [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md) containing the news to be added |

#### Samples
