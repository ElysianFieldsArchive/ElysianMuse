package org.darkSolace.muse.mail.repository

import org.darkSolace.muse.mail.model.MailerSettings
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MailerSettingsRepository : CrudRepository<MailerSettings, Long>