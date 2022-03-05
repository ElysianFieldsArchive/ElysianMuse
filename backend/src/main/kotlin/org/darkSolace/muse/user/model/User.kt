package org.darkSolace.muse.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.springframework.security.crypto.bcrypt.BCrypt
import java.util.*
import javax.persistence.*

/**
 * The [User] model class
 *
 * Holds all values containing to a registered user.
 */
@Entity
@Table(name = "museUser", uniqueConstraints = [UniqueConstraint(columnNames = ["username", "email"])])
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,
    val username: String,
    @JsonIgnore
    var password: String,
    @JsonIgnore
    var salt: String = BCrypt.gensalt(),
    var email: String,
    var realName: String? = null,
    @Temporal(TemporalType.TIMESTAMP)
    val signUpDate: Date = Date(),
    @Temporal(TemporalType.TIMESTAMP)
    var lastLogInDate: Date? = null,
    var bio: String? = null,
    @Temporal(TemporalType.DATE)
    var birthday: Date? = null,
    var validatedAuthor: Boolean = false,
    var onProbation: Boolean = false,
    @OneToOne(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    var userSettings: UserSettings = UserSettings(),
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.ORDINAL)
    val userTags: MutableSet<UserTag> = mutableSetOf(),
    @OneToOne
    @Cascade(CascadeType.ALL)
    var avatar: Avatar? = null,
    @Enumerated(EnumType.ORDINAL)
    var role: Role = Role.MEMBER
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id, username = $username, email = $email, " +
                "realName = $realName, signUpDate = $signUpDate, lastLogInDate = $lastLogInDate, bio = $bio, " +
                "birthday = $birthday, validatedAuthor = $validatedAuthor, onProbation = $onProbation, " +
                "userSettings = $userSettings, userTags = $userTags, avatar = $avatar, roles = $role)"
    }

    fun toPublicUser(): User {
        val user = User(id, username, "", "", "")
        user.role = role

        if (userSettings.emailVisible) user.email = email
        if (userSettings.birthdayVisible) user.birthday = birthday
        if (userSettings.realNameVisible) user.realName = realName

        return user
    }
}
