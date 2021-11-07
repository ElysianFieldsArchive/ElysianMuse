package org.darkSolace.muse.userModule.repository

import org.darkSolace.muse.userModule.model.Avatar
import org.darkSolace.muse.userModule.model.UserSettings
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * CRUD Repository for the [UserSettings] entity.
 */
@Repository
interface UserSettingsRepository : CrudRepository<UserSettings, Long>
