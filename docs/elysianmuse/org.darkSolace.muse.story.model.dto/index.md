//[elysianmuse](../../index.md)/[org.darkSolace.muse.story.model.dto](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ChapterCommentDTO](-chapter-comment-d-t-o/index.md) | [jvm]<br>class [ChapterCommentDTO](-chapter-comment-d-t-o/index.md) |
| [ChapterDTO](-chapter-d-t-o/index.md) | [jvm]<br>class [ChapterDTO](-chapter-d-t-o/index.md) |
| [FilterStoriesDTO](-filter-stories-d-t-o/index.md) | [jvm]<br>class [FilterStoriesDTO](-filter-stories-d-t-o/index.md)(val ratings: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Rating](../org.darkSolace.muse.story.model/-rating/index.md)&gt; = emptyList(), val tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[StoryTag](../org.darkSolace.muse.story.model/-story-tag/index.md)&gt; = emptyList(), val authors: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[User](../org.darkSolace.muse.user.model/-user/index.md)&gt; = emptyList()) |
| [StoryChapterContributorDTO](-story-chapter-contributor-d-t-o/index.md) | [jvm]<br>class [StoryChapterContributorDTO](-story-chapter-contributor-d-t-o/index.md)(val user: [UserIdNameDTO](../org.darkSolace.muse.user.model.dto/-user-id-name-d-t-o/index.md), val userTag: [UserTag](../org.darkSolace.muse.user.model/-user-tag/index.md)) |
| [StoryDTO](-story-d-t-o/index.md) | [jvm]<br>class [StoryDTO](-story-d-t-o/index.md) |
| [UserContributionDTO](-user-contribution-d-t-o/index.md) | [jvm]<br>data class [UserContributionDTO](-user-contribution-d-t-o/index.md)(val stories: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[StoryDTO](-story-d-t-o/index.md)&gt;, val chapters: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[ChapterDTO](-chapter-d-t-o/index.md)&gt;) |
