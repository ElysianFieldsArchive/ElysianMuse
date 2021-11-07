package org.darkSolace.muse.userModule.service

import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.model.UserTag
import org.darkSolace.muse.userModule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for [UserTag] related tasks when working with [User]s.
 *
 * @see UserService
 */
@Service
class UserTagService(@Autowired val userRepository: UserRepository) {

    /**
     * Adds a [UserTag] to a [User]
     *
     * @param user the [User] to be modified
     * @param tag the [UserTag] to be added
     * @return the modified [User] or null if [User] does not exist
     */
    @Transactional
    fun addTagToUser(user: User, tag: UserTag): User? {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }
        if(changedUser == null)
            return null

        changedUser.userTags.add(tag)
        if (tag.name.endsWith("_INACTIVE")) {
            val activeTag = UserTag.values().firstOrNull { it.name == tag.name.removeSuffix("_INACTIVE") }
            if (activeTag != null)
                removeTagFromUser(changedUser, activeTag)
        } else {
            val inactiveTag = UserTag.values().firstOrNull { it.name == (tag.name + "_INACTIVE") }
            if (inactiveTag != null)
                removeTagFromUser(changedUser, inactiveTag)
        }
        userRepository.save(changedUser)
        return changedUser
    }

    /**
     * Removes a [UserTag] to a [User]
     *
     * @param user the [User] to be modified
     * @param tag the [UserTag] to be removed
     * @return the modified [User] or null if [User] does not exist
     */
    @Transactional
    fun removeTagFromUser(user: User, tag: UserTag): User? {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }
        if(changedUser == null)
            return null

        changedUser.userTags.remove(tag)
        userRepository.save(changedUser)
        return changedUser
    }
}
