package org.darkSolace.muse.mail.repository

import org.darkSolace.muse.mail.model.MailTemplate
import org.darkSolace.muse.mail.model.TemplateType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * CRUD repository for the [MailTemplate] entity
 */
@Repository
interface MailTemplateRepository : CrudRepository<MailTemplate, Long> {
    /**
     * Retrieves a [MailTemplate] idientified by its [TemplateType]
     *
     * @param templateType the [TemplateType] of the template to be retrieved
     * @return the mail template
     *
     * @see [TemplateType]
     */
    fun findByType(templateType: TemplateType): MailTemplate
}
