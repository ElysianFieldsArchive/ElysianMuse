package org.darkSolace.muse.story.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.Hibernate

/**
 * Placeholder for a story tag class
 */
@Entity
data class StoryTag(
    @Id
    var id: Long? = null,
    val tagName: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as StoryTag

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , tagName = $tagName )"
    }
}
