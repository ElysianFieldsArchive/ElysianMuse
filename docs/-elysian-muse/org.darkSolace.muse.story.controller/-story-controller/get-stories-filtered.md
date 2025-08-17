//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.controller](../index.md)/[StoryController](index.md)/[getStoriesFiltered](get-stories-filtered.md)

# getStoriesFiltered

[jvm]\

@PostMapping(value = [&quot;/filtered&quot;])

fun [getStoriesFiltered](get-stories-filtered.md)(@RequestBodyfilters: [FilterStoriesDTO](../../org.darkSolace.muse.story.model.dto/-filter-stories-d-t-o/index.md)): [Collection](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-collection/index.html)&lt;[StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)&gt;

Retrieves a list of [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)s, filtered by the provided filters

#### Return

ResponseEntity containing a list of [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)s

#### Parameters

jvm

| | |
|---|---|
| filters | [FilterStoriesDTO](../../org.darkSolace.muse.story.model.dto/-filter-stories-d-t-o/index.md) specifying the values to filter by |