package org.darkSolace.muse.userModule.service

import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRoleService(@Autowired val userRepository: UserRepository) {

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
}
