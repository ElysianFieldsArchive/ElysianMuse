package org.darkSolace.muse.mail.model

import org.hibernate.Hibernate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class MailerSettings(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val protocol: String = "smtp",
    val auth: Boolean = true,
    val startTLS: Boolean = true,
    val fromAddress: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MailerSettings

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , host = $host , port = $port , username = $username , password = **** , protocol = $protocol , auth = $auth , startTLS = $startTLS )"
    }
}