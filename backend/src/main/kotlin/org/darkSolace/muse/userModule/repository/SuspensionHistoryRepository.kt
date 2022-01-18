package org.darkSolace.muse.userModule.repository

import org.darkSolace.muse.userModule.model.SuspensionHistoryEntry
import org.darkSolace.muse.userModule.model.User
import org.springframework.data.repository.CrudRepository

interface SuspensionHistoryRepository : CrudRepository<SuspensionHistoryEntry, Long> {
    fun getById(id: Long): SuspensionHistoryEntry?
    fun getByUserAndAcceptedDateIsNull(user: User): SuspensionHistoryEntry?
    fun getByConfirmationCode(confirmationCode: String): SuspensionHistoryEntry?
    fun findAllByUserOrderBySuspendedDate(user: User): List<SuspensionHistoryEntry>
}
