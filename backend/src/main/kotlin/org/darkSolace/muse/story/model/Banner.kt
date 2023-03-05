package org.darkSolace.muse.story.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Lob

@Entity
class Banner {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Lob
    var blob: ByteArray = ByteArray(0)
}
