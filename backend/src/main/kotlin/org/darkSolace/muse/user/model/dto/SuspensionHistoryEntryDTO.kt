package org.darkSolace.muse.user.model.dto

import org.darkSolace.muse.user.model.SuspensionHistoryEntry
import java.util.*

class SuspensionHistoryEntryDTO {
    var id: Long? = null
    var user: UserIdNameDTO? = null
    var suspendedDate: Date = Date()
    var confirmationCode: String = ""
    var acceptedDate: Date? = null
    var confirmationDetails: String? = null

    companion object {
        fun fromList(list: List<SuspensionHistoryEntry>) = list.map { from(it) }
        fun from(entry: SuspensionHistoryEntry) = SuspensionHistoryEntryDTO().apply {
            id = entry.id
            user = UserIdNameDTO.from(entry.user)
            suspendedDate = entry.suspendedDate
            confirmationCode = entry.confirmationCode
            acceptedDate = entry.acceptedDate
            confirmationDetails = entry.confirmationDetails
        }
    }
}