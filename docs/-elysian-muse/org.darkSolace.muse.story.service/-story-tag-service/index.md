//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.service](../index.md)/[StoryTagService](index.md)

# StoryTagService

[jvm]\
@Service

class [StoryTagService](index.md)(@Autowiredval storyRepository: [StoryRepository](../../org.darkSolace.muse.story.repository/-story-repository/index.md), @Autowiredval storyTagRepository: [StoryTagRepository](../../org.darkSolace.muse.story.repository/-story-tag-repository/index.md))

## Constructors

| | |
|---|---|
| [StoryTagService](-story-tag-service.md) | [jvm]<br>constructor(@AutowiredstoryRepository: [StoryRepository](../../org.darkSolace.muse.story.repository/-story-repository/index.md), @AutowiredstoryTagRepository: [StoryTagRepository](../../org.darkSolace.muse.story.repository/-story-tag-repository/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [storyRepository](story-repository.md) | [jvm]<br>val [storyRepository](story-repository.md): [StoryRepository](../../org.darkSolace.muse.story.repository/-story-repository/index.md) |
| [storyTagRepository](story-tag-repository.md) | [jvm]<br>val [storyTagRepository](story-tag-repository.md): [StoryTagRepository](../../org.darkSolace.muse.story.repository/-story-tag-repository/index.md) |

## Functions

| Name | Summary |
|---|---|
| [createStoryTag](create-story-tag.md) | [jvm]<br>fun [createStoryTag](create-story-tag.md)(tag: [StoryTag](../../org.darkSolace.muse.story.model/-story-tag/index.md)): [StoryTag](../../org.darkSolace.muse.story.model/-story-tag/index.md) |
| [deleteStoryTag](delete-story-tag.md) | [jvm]<br>fun [deleteStoryTag](delete-story-tag.md)(tag: [StoryTag](../../org.darkSolace.muse.story.model/-story-tag/index.md)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>TODO: Controller methods and unittests - consult with Bibi |
| [replaceStoryTag](replace-story-tag.md) | [jvm]<br>fun [replaceStoryTag](replace-story-tag.md)(oldTag: [StoryTag](../../org.darkSolace.muse.story.model/-story-tag/index.md), newTag: [StoryTag](../../org.darkSolace.muse.story.model/-story-tag/index.md))<br>TODO: Controller methods and unittests - consult with Bibi |