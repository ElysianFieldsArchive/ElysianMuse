package org.darkSolace.muse.userModule.repository

import org.darkSolace.muse.userModule.model.UserSettings
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserSettingsRepository : CrudRepository<UserSettings, Long>
