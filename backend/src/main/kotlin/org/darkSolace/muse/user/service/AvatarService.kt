package org.darkSolace.muse.user.service

import org.darkSolace.muse.user.model.Avatar
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.repository.AvatarRepository
import org.darkSolace.muse.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AvatarService(@Autowired val userRepository: UserRepository, @Autowired val avatarRepository: AvatarRepository) {
    /**
     * Changes the [Avatar] for the specified [User] and persists it in the database.
     *
     * @param user the [User] to modify
     * @param avatar the new [Avatar]
     * @return the modified [User] or `null` if the [User] was not found
     */
    @Transactional
    fun changeAvatar(user: User, avatar: Avatar): User? {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }
        if (changedUser == null)
            return null

        // just changes the avatar blob if an avatar already exists
        if (changedUser.avatar == null) {
            avatarRepository.save(avatar)
            changedUser.avatar = avatar
            userRepository.save(changedUser)
        } else {
            changedUser.avatar?.avatarBlob = avatar.avatarBlob
            userRepository.save(changedUser)
        }

        return changedUser
    }
}
