package org.dark_solace.muse.userModule.repository

import org.dark_solace.muse.userModule.model.UserSettings
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserSettingsRepository : CrudRepository<UserSettings, Long>