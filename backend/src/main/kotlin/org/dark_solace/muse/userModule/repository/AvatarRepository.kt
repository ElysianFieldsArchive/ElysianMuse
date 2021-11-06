package org.dark_solace.muse.userModule.repository

import org.dark_solace.muse.userModule.model.Avatar
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AvatarRepository : CrudRepository<Avatar, Long>