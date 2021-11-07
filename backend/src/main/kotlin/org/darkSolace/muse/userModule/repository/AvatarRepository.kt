package org.darkSolace.muse.userModule.repository

import org.darkSolace.muse.userModule.model.Avatar
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * CRUD Repository for the [Avatar] entity.
 */
@Repository
interface AvatarRepository : CrudRepository<Avatar, Long>
