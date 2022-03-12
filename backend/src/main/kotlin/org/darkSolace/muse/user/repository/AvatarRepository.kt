package org.darkSolace.muse.user.repository

import org.darkSolace.muse.user.model.Avatar
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * CRUD Repository for the [Avatar] entity.
 */
@Repository
interface AvatarRepository : CrudRepository<Avatar, Long>
