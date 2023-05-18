//[elysianmuse](../../../index.md)/[org.darkSolace.muse.news.controller](../index.md)/[NewsController](index.md)/[editNews](edit-news.md)

# editNews

[jvm]\

@PostMapping(value = [&quot;/{id}&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MODERATOR', 'ADMINISTRATOR')&quot;)

fun [editNews](edit-news.md)(@PathVariableid: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestBody@Validnews: [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Edits a news entry. To edit news the [org.darkSolace.muse.user.model.Role](../../org.darkSolace.muse.user.model/-role/index.md) of [org.darkSolace.muse.user.model.Role.ADMINISTRATOR](../../org.darkSolace.muse.user.model/-role/-a-d-m-i-n-i-s-t-r-a-t-o-r/index.md) or [org.darkSolace.muse.user.model.Role.MODERATOR](../../org.darkSolace.muse.user.model/-role/-m-o-d-e-r-a-t-o-r/index.md) is required.

#### Parameters

jvm

| | |
|---|---|
| news | the [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md) containing the edited news |
| id | the id of the [NewsEntryDTO](../../org.darkSolace.muse.news.model.dto/-news-entry-d-t-o/index.md) to be edited |

#### Samples
