package org.darkSolace.muse.layoutModule.model

import javax.persistence.Entity
import javax.persistence.Id

/**
 * Placeholder for a Layout class
 */
@Entity
data class Layout(
    @Id
    var id: Long? = null
)
