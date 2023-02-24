//[elysianmuse](../../../index.md)/[org.darkSolace.muse.story.model](../index.md)/[ChapterComment](index.md)

# ChapterComment

[jvm]\
@Entity

class [ChapterComment](index.md)

## Constructors

|                                       |                                                      |
|---------------------------------------|------------------------------------------------------|
| [ChapterComment](-chapter-comment.md) | [jvm]<br>fun [ChapterComment](-chapter-comment.md)() |

## Properties

| Name                                     | Summary                                                                                                                                       |
|------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| [author](author.md)                      | [jvm]<br>var [author](author.md): [User](../../org.darkSolace.muse.user.model/-user/index.md)? = null                                         |
| [authorApproved](author-approved.md)     | [jvm]<br>var [authorApproved](author-approved.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [chapter](chapter.md)                    | [jvm]<br>var [chapter](chapter.md): [Chapter](../-chapter/index.md)? = null                                                                   |
| [content](content.md)                    | [jvm]<br>var [content](content.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)                          |
| [id](id.md)                              | [jvm]<br>var [id](id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null                                |
| [publishedDate](published-date.md)       | [jvm]<br>var [publishedDate](published-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)                        |
| [referenceComment](reference-comment.md) | [jvm]<br>var [referenceComment](reference-comment.md): [ChapterComment](index.md)? = null                                                     |
