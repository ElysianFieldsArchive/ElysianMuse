package org.darkSolace.muse.privMessages.model.dto

import org.darkSolace.muse.DTO
import org.darkSolace.muse.privMessages.model.MessageDirection
import org.darkSolace.muse.privMessages.model.PrivateMessage
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import java.util.*

class PrivateMessageDTO {
    var id: Long? = null
    var direction: MessageDirection = MessageDirection.OUTGOING
    var sender: UserIdNameDTO? = null
    var recipient: UserIdNameDTO? = null
    var subject: String = ""
    var content: String = ""
    var sentDate: Date = Date()
    var isRead: Boolean = false
    var inReplyTo: Long? = null

    companion object : DTO<PrivateMessage, PrivateMessageDTO> {
        override fun from(item: PrivateMessage) = PrivateMessageDTO().apply {
            id = item.id
            direction = MessageDirection.OUTGOING
            sender = UserIdNameDTO.from(item.sender)
            recipient = UserIdNameDTO.from(item.recipient)
            subject = item.subject
            content = item.content
            sentDate = item.sentDate
            isRead = item.isRead
            inReplyTo = item.inReplyTo?.id
        }
    }
}
