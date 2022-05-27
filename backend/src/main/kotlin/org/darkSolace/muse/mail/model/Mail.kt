package org.darkSolace.muse.mail.model

import org.darkSolace.muse.user.model.User
import org.hibernate.Hibernate
import javax.persistence.*

/**
 * This [Mail] class holds all information required to be sent via email.
 * Mainly used by [MailQueueEntry] to be sent via the [org.darkSolace.muse.mail.service.MailService]
 *
 * @see MailQueueEntry
 * @see org.darkSolace.muse.mail.service.MailService
 */
@Entity
data class Mail(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @OneToOne
    val recipient: User,
    val subject: String,

    @Lob
    val content: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Mail

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , recipient = $recipient , subject = $subject , content = $content )"
    }
}
