package org.darkSolace.muse.user.model.dto

import org.darkSolace.muse.user.model.User

class UserIdNameDTO {
    var id: Long? = null
    var username: String? = ""

    companion object {
        fun fromList(list: List<User>) = list.map { from(it) }
        fun from(user: User?) = UserIdNameDTO().apply {
            id = user?.id
            username = user?.username
        }
    }
}