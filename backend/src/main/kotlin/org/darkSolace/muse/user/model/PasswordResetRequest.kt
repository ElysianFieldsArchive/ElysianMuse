package org.darkSolace.muse.user.model

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

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
