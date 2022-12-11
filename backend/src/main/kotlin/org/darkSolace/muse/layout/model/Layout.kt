package org.darkSolace.muse.layout.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

/**
 * Placeholder for a Layout class
 */
@Entity
data class Layout(
    @Id
    var id: Long? = null
)
