package org.darkSolace.muse.mail.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import org.springframework.validation.annotation.Validated

/**
 * Holds the settings for a smtp server to be used to send emails
 *
 * @see org.darkSolace.muse.mail.service.MailService
 */
@Entity
@Validated
data class MailerSettings(
    @Id
    @GeneratedValue
    var id: Long? = null
) {

    @NotNull
    var host: String = ""

    @Min(1)
    var port: Int = -1

    @NotNull
    var username: String = ""

    @NotNull
    var password: String = ""
    val protocol: String = "smtp"
    val auth: Boolean = true
    val startTLS: Boolean = true
    var fromAddress: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MailerSettings

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , host = $host , port = $port , username = $username , " +
                "password = **** , protocol = $protocol , auth = $auth , startTLS = $startTLS )"
    }
}
