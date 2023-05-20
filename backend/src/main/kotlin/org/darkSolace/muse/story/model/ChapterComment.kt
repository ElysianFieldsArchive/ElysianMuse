package org.darkSolace.muse.story.model

import jakarta.persistence.Basic
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.darkSolace.muse.user.model.User
import java.util.*

@Entity
class ChapterComment {
    @Id
    @GeneratedValue
    var id: Long? = null

    var authorApproved: Boolean = false

    @ManyToOne
    lateinit var author: User

    var chapterId: Long? = null

    @Lob
    @Basic(fetch = FetchType.LAZY)
    var content: String = ""

    @Temporal(TemporalType.TIMESTAMP)
    var publishedDate: Date = Date()

    var referenceComment: Long? = null
}
