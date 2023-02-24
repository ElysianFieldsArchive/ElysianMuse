package org.darkSolace.muse.privMessages.model.dto

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
    var inReplyTo: PrivateMessage? = null

    companion object {
        fun fromPrivateMessageList(list: List<PrivateMessage>) = list.map { from(it) }
        fun from(privateMessage: PrivateMessage) = PrivateMessageDTO().apply {
            id = privateMessage.id
            direction = MessageDirection.OUTGOING
            sender = UserIdNameDTO.from(privateMessage.sender)
            recipient = UserIdNameDTO.from(privateMessage.recipient)
            subject = privateMessage.subject
            content = privateMessage.content
            sentDate = privateMessage.sentDate
            isRead = privateMessage.isRead
            inReplyTo = privateMessage.inReplyTo
        }
    }
}
