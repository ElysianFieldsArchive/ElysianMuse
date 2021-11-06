package org.darkSolace.muse.userModule.repository

import org.darkSolace.muse.userModule.model.Avatar
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AvatarRepository : CrudRepository<Avatar, Long>
