package org.darkSolace.muse.user.model.dto

import org.darkSolace.muse.user.model.User

class UserIdNameDTO(user: User?) {
    val id = user?.id
    val username = user?.username

    companion object {
        fun fromList(list: List<User>) = list.map { UserIdNameDTO(it) }
    }
}