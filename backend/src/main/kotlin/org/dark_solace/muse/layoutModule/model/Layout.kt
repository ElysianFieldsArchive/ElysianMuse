package org.dark_solace.muse.layoutModule.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Layout(
    @Id
    var id: Long? = null
)