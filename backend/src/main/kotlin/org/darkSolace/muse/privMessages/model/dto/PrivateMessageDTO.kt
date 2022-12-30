package org.darkSolace.muse.privMessages.model.dto

import org.darkSolace.muse.privMessages.model.MessageDirection
import org.darkSolace.muse.privMessages.model.PrivateMessage
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import java.util.*

class PrivateMessageDTO(privateMessage: PrivateMessage) {
    var id: Long? = privateMessage.id
    var direction: MessageDirection = MessageDirection.OUTGOING
    var sender: UserIdNameDTO = UserIdNameDTO(privateMessage.sender)
    var recipient: UserIdNameDTO = UserIdNameDTO(privateMessage.recipient)
    var subject: String = privateMessage.subject
    var content: String = privateMessage.content
    var sentDate: Date = privateMessage.sentDate
    var isRead: Boolean = privateMessage.isRead
    var inReplyTo: PrivateMessage? = privateMessage.inReplyTo

    companion object {
        fun fromPrivateMessageList(list: List<PrivateMessage>) = list.map {
            PrivateMessageDTO(it)
        }
    }
}