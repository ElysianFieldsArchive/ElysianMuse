//[ElysianMuse](../../../index.md)/[org.darkSolace.muse.story.service](../index.md)/[StoryChapterService](index.md)

# StoryChapterService

[jvm]\
@Service

class [StoryChapterService](index.md)(@AutowiredstoryRepository: [StoryRepository](../../org.darkSolace.muse.story.repository/-story-repository/index.md), @AutowiredchapterRepository: [ChapterRepository](../../org.darkSolace.muse.story.repository/-chapter-repository/index.md), @AutowiredchapterCommentRepository: [ChapterCommentRepository](../../org.darkSolace.muse.story.repository/-chapter-comment-repository/index.md), @AutowireduserService: [UserService](../../org.darkSolace.muse.user.service/-user-service/index.md))

## Constructors

| | |
|---|---|
| [StoryChapterService](-story-chapter-service.md) | [jvm]<br>constructor(@AutowiredstoryRepository: [StoryRepository](../../org.darkSolace.muse.story.repository/-story-repository/index.md), @AutowiredchapterRepository: [ChapterRepository](../../org.darkSolace.muse.story.repository/-chapter-repository/index.md), @AutowiredchapterCommentRepository: [ChapterCommentRepository](../../org.darkSolace.muse.story.repository/-chapter-comment-repository/index.md), @AutowireduserService: [UserService](../../org.darkSolace.muse.user.service/-user-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addChapter](add-chapter.md) | [jvm]<br>fun [addChapter](add-chapter.md)(newChapter: [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |
| [addChapterComment](add-chapter-comment.md) | [jvm]<br>fun [addChapterComment](add-chapter-comment.md)(chapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), comment: [ChapterCommentDTO](../../org.darkSolace.muse.story.model.dto/-chapter-comment-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |
| [addContributorToChapter](add-contributor-to-chapter.md) | [jvm]<br>fun [addContributorToChapter](add-contributor-to-chapter.md)(storyId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), chapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), userId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), userTag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |
| [deleteChapter](delete-chapter.md) | [jvm]<br>fun [deleteChapter](delete-chapter.md)(id: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |
| [deleteChapterComment](delete-chapter-comment.md) | [jvm]<br>fun [deleteChapterComment](delete-chapter-comment.md)(chapterCommentId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |
| [editChapter](edit-chapter.md) | [jvm]<br>fun [editChapter](edit-chapter.md)(chapter: [ChapterDTO](../../org.darkSolace.muse.story.model.dto/-chapter-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |
| [editChapterComment](edit-chapter-comment.md) | [jvm]<br>fun [editChapterComment](edit-chapter-comment.md)(editedChapterComment: [ChapterCommentDTO](../../org.darkSolace.muse.story.model.dto/-chapter-comment-d-t-o/index.md)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |
| [getChapterById](get-chapter-by-id.md) | [jvm]<br>fun [getChapterById](get-chapter-by-id.md)(chapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)): [Chapter](../../org.darkSolace.muse.story.model/-chapter/index.md)? |
| [removeContributorFromChapter](remove-contributor-from-chapter.md) | [jvm]<br>fun [removeContributorFromChapter](remove-contributor-from-chapter.md)(storyId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), chapterId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), userId: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), userTag: [UserTag](../../org.darkSolace.muse.user.model/-user-tag/index.md)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |