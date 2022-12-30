package org.darkSolace.muse.user.model.dto

import org.darkSolace.muse.user.model.SuspensionHistoryEntry
import java.util.*

class SuspensionHistoryEntryDTO(entry: SuspensionHistoryEntry) {
    val id: Long? = entry.id
    val user: UserIdNameDTO = UserIdNameDTO(entry.user)
    val suspendedDate: Date = entry.suspendedDate
    val confirmationCode: String = entry.confirmationCode
    var acceptedDate: Date? = entry.acceptedDate
    var confirmationDetails: String? = entry.confirmationDetails

    companion object {
        fun fromList(list: List<SuspensionHistoryEntry>) = list.map { SuspensionHistoryEntryDTO(it) }
    }
}