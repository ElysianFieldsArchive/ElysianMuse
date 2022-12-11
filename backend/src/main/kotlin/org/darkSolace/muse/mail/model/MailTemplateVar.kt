package org.darkSolace.muse.mail.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.Hibernate

/**
 * A [MailTemplateVar] can be used to create variable content within a [MailTemplate].
 * [MailTemplateVar]s have to be specified as "<$templateVar>" within the template.
 */
@Entity
data class MailTemplateVar(
    val templateVar: String,
    val description: String,
    @Id
    @GeneratedValue
    val id: Long? = null,
    val systemManaged: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MailTemplateVar

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , templateVar = $templateVar , description = $description , " +
                "systemManaged = $systemManaged )"
    }
}
