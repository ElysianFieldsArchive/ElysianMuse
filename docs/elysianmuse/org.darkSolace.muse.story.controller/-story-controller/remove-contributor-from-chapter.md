//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[removeContributorFromChapter](remove-contributor-from-chapter.md)

# removeContributorFromChapter

[jvm]\

@DeleteMapping(value = [&quot;/{storyId}/{chapterId}/contributor&quot;])

@PreAuthorize(value = &quot;hasAnyAuthority('MEMEBR', 'MODERATOR', 'ADMINISTRATOR')&quot;)

fun [removeContributorFromChapter](remove-contributor-from-chapter.md)(@PathVariablestoryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @PathVariablechapterId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), @RequestParam@ValiduserTag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md), @RequestParam@Validuser: [User](../../org.darkSolace.muse.user.model/-user/index.md), authentication: Authentication?): ResponseEntity&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;
