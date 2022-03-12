package org.darkSolace.muse.user.repository

import org.darkSolace.muse.user.model.UserSettings
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * CRUD Repository for the [UserSettings] entity.
 */
@Repository
interface UserSettingsRepository : CrudRepository<UserSettings, Long>
