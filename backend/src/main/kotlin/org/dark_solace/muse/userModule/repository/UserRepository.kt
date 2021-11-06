package org.dark_solace.muse.userModule.repository

import org.dark_solace.muse.userModule.model.Role
import org.dark_solace.muse.userModule.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(username: String): User
    fun findAllByRole(role: Role): List<User>
}