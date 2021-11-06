package org.darkSolace.muse.userModule.service

import org.darkSolace.muse.userModule.model.*
import org.darkSolace.muse.userModule.repository.AvatarRepository
import org.darkSolace.muse.userModule.repository.UserRepository
import org.darkSolace.muse.userModule.repository.UserSettingsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    @Autowired val userRepository: UserRepository,
    @Autowired val avatarRepository: AvatarRepository,
    @Autowired val userSettingsRepository: UserSettingsRepository
) {

    @Transactional
    fun createUser(user: User): User {
        userRepository.save(user)
        return user
    }

    @Transactional
    fun suspendUser(user: User): User {
        changeRole(user, Role.SUSPENDED)
        return user
    }

    @Transactional
    fun changeRole(user: User, role: Role): User {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }

        changedUser.role = role
        userRepository.save(changedUser)
        return changedUser
    }

    @Transactional
    fun changeAvatar(user: User, avatar: Avatar): User {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }

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

    @Transactional
    fun getById(id: Long) = userRepository.findByIdOrNull(id)

    @Transactional
    fun getAll(): List<User> = userRepository.findAll().toList()

    @Transactional
    fun getAllWithRole(role: Role) = userRepository.findAllByRole(role)

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

    @Transactional
    fun deleteUser(user: User) {
        val deletedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }

        userRepository.delete(deletedUser)
    }

    @Transactional
    fun updateUserSettings(user: User, settings: UserSettings) {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }

        val oldSettings = changedUser.userSettings
        userSettingsRepository.save(settings)
        changedUser.userSettings = settings
        userRepository.save(changedUser)
        userSettingsRepository.delete(oldSettings)
    }
}
