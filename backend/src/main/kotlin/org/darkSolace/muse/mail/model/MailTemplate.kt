package org.darkSolace.muse.mail.model

import jakarta.persistence.*
import org.hibernate.annotations.*
import org.hibernate.annotations.CascadeType


/**
 * Model class representing a [MailTemplate] to be used to send out standardized emails.
 * The template content can contain variables to be substituted during email creation.
 */
@Entity
class MailTemplate(
    val type: TemplateType? = null
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Lob
    @Basic
    var mailContent: String = ""
    var mailSubject: String = ""

    @OneToMany
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    val templateVars: MutableSet<MailTemplateVar> = mutableSetOf()

    @ElementCollection(fetch = FetchType.EAGER)
    val templateVarValues: MutableMap<String, String> = mutableMapOf()
}
