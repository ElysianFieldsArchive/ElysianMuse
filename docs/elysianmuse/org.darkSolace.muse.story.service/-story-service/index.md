//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.service](../index.md)/[StoryService](index.md)

# StoryService

[jvm]\
@Service

class [StoryService](index.md)(@AutowiredstoryRepository: [StoryRepository](../../org.darkSolace.muse.story.repository/-story-repository/index.md), @AutowiredchapterRepository: [ChapterRepository](../../org.darkSolace.muse.story.repository/-chapter-repository/index.md), @AutowiredchapterCommentRepository: [ChapterCommentRepository](../../org.darkSolace.muse.story.repository/-chapter-comment-repository/index.md), @AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md))

## Constructors

| | |
|---|---|
| [StoryService](-story-service.md) | [jvm]<br>constructor(@AutowiredstoryRepository: [StoryRepository](../../org.darkSolace.muse.story.repository/-story-repository/index.md), @AutowiredchapterRepository: [ChapterRepository](../../org.darkSolace.muse.story.repository/-chapter-repository/index.md), @AutowiredchapterCommentRepository: [ChapterCommentRepository](../../org.darkSolace.muse.story.repository/-chapter-comment-repository/index.md), @AutowireduserRepository: [UserRepository](../../org.darkSolace.muse.user.repository/-user-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addChapter](add-chapter.md) | [jvm]<br>fun [addChapter](add-chapter.md)(newChapter: [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [addContributor](add-contributor.md) | [jvm]<br>fun [addContributor](add-contributor.md)(storyId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), userId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), userTag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [createStory](create-story.md) | [jvm]<br>fun [createStory](create-story.md)(story: [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [deleteChapter](delete-chapter.md) | [jvm]<br>fun [deleteChapter](delete-chapter.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [deleteStory](delete-story.md) | [jvm]<br>fun [deleteStory](delete-story.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [editChapter](edit-chapter.md) | [jvm]<br>fun [editChapter](edit-chapter.md)(chapter: [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [editStory](edit-story.md) | [jvm]<br>fun [editStory](edit-story.md)(story: [StoryDTO](../../org.darkSolace.muse.story.model.dto/-story-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getAllStories](get-all-stories.md) | [jvm]<br>fun [getAllStories](get-all-stories.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Story](../../org.darkSolace.muse.story.model/-story/index.md)&gt; |
| [getStoriesFiltered](get-stories-filtered.md) | [jvm]<br>fun [getStoriesFiltered](get-stories-filtered.md)(ratings: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Rating](../../org.darkSolace.muse.story.model/-rating/index.md)&gt;? = null, tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[StoryTag](../../org.darkSolace.muse.story.model/-story-tag/index.md)&gt;? = null): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Story](../../org.darkSolace.muse.story.model/-story/index.md)&gt; |
| [getStoryById](get-story-by-id.md) | [jvm]<br>fun [getStoryById](get-story-by-id.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Story](../../org.darkSolace.muse.story.model/-story/index.md)? |
