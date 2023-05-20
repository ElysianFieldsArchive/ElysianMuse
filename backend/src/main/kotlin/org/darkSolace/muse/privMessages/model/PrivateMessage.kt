package org.darkSolace.muse.privMessages.model

import com.fasterxml.jackson.annotation.JsonIdentityReference
import jakarta.persistence.*
import org.darkSolace.muse.user.model.User
import org.hibernate.Hibernate
import java.util.*

/**
 * This [PrivateMessage] class holds all information used for direct communication between [User]s.
 */
@Entity
data class PrivateMessage(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Enumerated
    var direction: MessageDirection = MessageDirection.OUTGOING,

    @ManyToOne
    var sender: User,

    @ManyToOne
    var recipient: User,

    var subject: String = "",

    @Lob
    @Basic
    var content: String = "",

    @Temporal(TemporalType.TIMESTAMP)
    var sentDate: Date = Date(),

    var isRead: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference
    var inReplyTo: PrivateMessage? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PrivateMessage

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , direction = $direction , sender = $sender , " +
                "recipient = $recipient , subject = $subject , content = $content , sentDate = $sentDate , " +
                "isRead = $isRead )"
    }
}
