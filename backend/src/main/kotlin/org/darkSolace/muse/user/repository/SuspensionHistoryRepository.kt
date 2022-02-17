package org.darkSolace.muse.user.repository

import org.darkSolace.muse.user.model.SuspensionHistoryEntry
import org.darkSolace.muse.user.model.User
import org.springframework.data.repository.CrudRepository

interface SuspensionHistoryRepository : CrudRepository<SuspensionHistoryEntry, Long> {
    fun getByUserAndAcceptedDateIsNull(user: User): SuspensionHistoryEntry?
    fun getByConfirmationCode(confirmationCode: String): SuspensionHistoryEntry?
    fun findAllByUserOrderBySuspendedDate(user: User): List<SuspensionHistoryEntry>
}
