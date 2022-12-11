package org.darkSolace.muse.user.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.util.*

/**
 * Class holding a reset code to authorize the reset of a users password
 */
@Entity
class PasswordResetRequest(
    @Cascade(CascadeType.SAVE_UPDATE) @OneToOne val user: User,
    @Id @GeneratedValue val id: Long? = null
) {
    val code = UUID.randomUUID().toString()
}
