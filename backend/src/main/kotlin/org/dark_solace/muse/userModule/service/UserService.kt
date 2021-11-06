package org.dark_solace.muse.userModule.service

import org.dark_solace.muse.userModule.model.*
import org.dark_solace.muse.userModule.repository.AvatarRepository
import org.dark_solace.muse.userModule.repository.UserRepository
import org.dark_solace.muse.userModule.repository.UserSettingsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired val userRepository: UserRepository,
    @Autowired val avatarRepository: AvatarRepository,
    @Autowired val userSettingsRepository: UserSettingsRepository
) {

    fun createUser(user: User): User {
        userRepository.save(user)
        return user
    }

    fun suspendUser(user: User): User {
        changeRole(user, Role.SUSPENDED)
        return user
    }

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

    fun changeAvatar(user: User, avatar: Avatar) {
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
    }

    fun getById(id: Long) = userRepository.findByIdOrNull(id)

    fun getAll(): List<User> = userRepository.findAll().toList()

    fun getAllWithRole(role: Role) = userRepository.findAllByRole(role)

    fun addTagToUser(user: User, tag: UserTags) {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }

        changedUser.userTags.add(tag)
        if (tag.name.endsWith("_INACTIVE")) {
            try {
                val activeTag = UserTags.valueOf(tag.name.removeSuffix("_INACTIVE"))
                changedUser.userTags.remove(activeTag)
            } catch (e: IllegalArgumentException) {
                //do nothing
            }
        } else {
            try {
                val inactiveTag = UserTags.valueOf(tag.name + "_INACTIVE")
                changedUser.userTags.remove(inactiveTag)
            } catch (e: IllegalArgumentException) {
                //do nothing
            }
        }
        userRepository.save(changedUser)
    }

    fun removeTagFromUser(user: User, tag: UserTags) {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }

        changedUser.userTags.remove(tag)
        userRepository.save(changedUser)
    }

    fun deleteUser(user: User) {
        val deletedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }

        userRepository.delete(deletedUser)
    }

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