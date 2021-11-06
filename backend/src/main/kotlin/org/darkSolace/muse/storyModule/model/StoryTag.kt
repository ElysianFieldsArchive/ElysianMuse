package org.darkSolace.muse.storyModule.model

import org.hibernate.Hibernate
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class StoryTag (
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
