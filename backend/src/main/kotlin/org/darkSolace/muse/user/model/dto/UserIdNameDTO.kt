package org.darkSolace.muse.user.model.dto

import org.darkSolace.muse.DTO
import org.darkSolace.muse.user.model.User

class UserIdNameDTO {
    var id: Long? = null
    var username: String? = ""

    companion object : DTO<User, UserIdNameDTO> {
        override fun from(item: User) = UserIdNameDTO().apply {
            id = item.id
            username = item.username
        }
    }
}