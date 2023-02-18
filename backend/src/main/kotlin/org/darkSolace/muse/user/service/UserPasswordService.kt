package org.darkSolace.muse.user.service

import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.user.model.PasswordResetRequest
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.repository.PasswordResetRequestRepository
import org.darkSolace.muse.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

/**
 * Service for dealing with password changes
 */
@Service
class UserPasswordService(
    @Autowired val passwordResetRequestRepository: PasswordResetRequestRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val mailService: MailService,
) {
    /**
     * Generates a password reset code for a given user and sends it via email to the user.
     *
     * @param user the [User] resetting its password
     */
    fun generateAndSendPasswordResetCode(user: User) {
        val passwordResetCode = PasswordResetRequest(user)

        passwordResetRequestRepository.save(passwordResetCode)

        mailService.sendPasswordResetMail(user, passwordResetCode.code)
    }

    /**
     * Updates the password for a given user. Also generates a new salt.
     *
     * @param user the [User] changing its password
     * @param newPassword the new password
     */
    fun updatePassword(user: User, newPassword: String) {
        //generate new salt
        user.salt = BCrypt.gensalt()
        //hash the password before saving the user
        user.password = BCrypt.hashpw(newPassword, user.salt)

        userRepository.save(user)
    }

    /**
     * Finds a user identified by the password reset code
     *
     * @param passwordConfirmationCode the password reset code
     * @return the [User] or `null`
     */
    fun getUserByCode(passwordConfirmationCode: String): User? {
        val passwordResetRequest = passwordResetRequestRepository.findByCode(passwordConfirmationCode)

        return passwordResetRequest?.user
    }
}
