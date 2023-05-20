package org.darkSolace.muse.mail.model

import jakarta.persistence.Basic
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.OneToMany
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode


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
