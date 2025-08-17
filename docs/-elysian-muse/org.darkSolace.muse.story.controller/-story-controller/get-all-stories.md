//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[getAllStories](get-all-stories.md)

# getAllStories

[jvm]\

@GetMapping(value = [&quot;/all&quot;])

fun [getAllStories](get-all-stories.md)(): [Collection](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-collection/index.html)&lt;[StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)&gt;

Retrieves all [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)s

#### Return

ResponseEntity containing a list of [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)s