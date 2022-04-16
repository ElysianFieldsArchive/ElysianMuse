package org.darkSolace.muse.mail.repository

import org.darkSolace.muse.mail.model.MailTemplate
import org.darkSolace.muse.mail.model.TemplateType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MailTemplateRepository : CrudRepository<MailTemplate, Long> {
    fun findByType(templateType: TemplateType): MailTemplate
}