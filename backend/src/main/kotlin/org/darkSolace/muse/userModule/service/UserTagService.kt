package org.darkSolace.muse.userModule.service

import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.model.UserTags
import org.darkSolace.muse.userModule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserTagService(@Autowired val userRepository: UserRepository) {
    @Transactional
    fun addTagToUser(user: User, tag: UserTags): User {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }

        changedUser.userTags.add(tag)
        if (tag.name.endsWith("_INACTIVE")) {
            val activeTag = UserTags.values().first { it.name == tag.name.removeSuffix("_INACTIVE") }
            changedUser.userTags.remove(activeTag)
        } else {
            val inactiveTag = UserTags.values().first { it.name == (tag.name + "_INACTIVE") }
            changedUser.userTags.remove(inactiveTag)
        }
        userRepository.save(changedUser)
        return changedUser
    }

    @Transactional
    fun removeTagFromUser(user: User, tag: UserTags) : User {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }

        changedUser.userTags.remove(tag)
        userRepository.save(changedUser)
        return changedUser
    }
}
