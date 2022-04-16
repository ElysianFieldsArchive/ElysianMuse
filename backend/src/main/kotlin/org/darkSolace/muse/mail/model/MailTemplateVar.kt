package org.darkSolace.muse.mail.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class MailTemplateVar(
    val templateVar: String,
    val description: String,
    @Id
    @GeneratedValue
    val id: Long? = null,
    val system_managed: Boolean = false
)