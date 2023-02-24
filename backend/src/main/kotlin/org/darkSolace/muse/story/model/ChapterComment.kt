package org.darkSolace.muse.story.model

import jakarta.persistence.*
import org.darkSolace.muse.user.model.User
import java.util.*

@Entity
class ChapterComment {
    @Id
    @GeneratedValue
    var id: Long? = null

    var authorApproved: Boolean = false

    @ManyToOne
    var author: User? = null

    @ManyToOne
    var chapter: Chapter? = null

    @Lob
    var content: String = ""

    @Temporal(TemporalType.TIMESTAMP)
    var publishedDate: Date = Date()

    @OneToOne(optional = true)
    var referenceComment: ChapterComment? = null
}
