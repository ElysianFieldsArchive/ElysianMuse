package org.darkSolace.muse.user.repository

import org.darkSolace.muse.user.model.PasswordResetRequest
import org.darkSolace.muse.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * CRUD repository for the [PasswordResetRequest] entity
 */
@Repository
interface PasswordResetRequestRepository : CrudRepository<PasswordResetRequest, Long> {
    /**
     * Retrieves a [PasswordResetRequest] for a given user
     *
     * @param user the user used to find the [PasswordResetRequest]
     *
     * @return the [PasswordResetRequest] or `null`
     */
    fun findByUser(user: User): PasswordResetRequest?

    /**
     * Retrieves a [PasswordResetRequest] identified by the reset code
     *
     * @param code the reset code
     *
     * @return the [PasswordResetRequest] or `null`
     */
    fun findByCode(code: String): PasswordResetRequest?
}
