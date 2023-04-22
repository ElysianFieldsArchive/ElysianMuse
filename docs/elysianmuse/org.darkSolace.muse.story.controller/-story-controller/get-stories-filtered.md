//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[getStoriesFiltered](get-stories-filtered.md)

# getStoriesFiltered

[jvm]\

@GetMapping(value = [&quot;/filtered&quot;])

fun [getStoriesFiltered](get-stories-filtered.md)(@RequestParam(required = false)ratings: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Rating](../../org.darkSolace.muse.story.model/-rating/index.md)&gt;, @RequestParam(required = false)tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[StoryTag](../../org.darkSolace.muse.story.model/-story-tag/index.md)&gt;, @RequestParam(required = false)users: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../../org.darkSolace.muse.user.model/-user/index.md)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)&gt;
