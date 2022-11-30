package org.darkSolace.muse.user.model

import jakarta.persistence.*
import org.darkSolace.muse.layout.model.FontFamily
import org.darkSolace.muse.layout.model.FontSize
import org.darkSolace.muse.layout.model.Layout
import org.darkSolace.muse.story.model.Rating
import org.darkSolace.muse.story.model.StoryTag
import org.hibernate.Hibernate

/**
 * The [UserSettings] model class, is part of a [User].
 *
 * Holds user configurations, e.g. which values should be visible on the user profile as well as default
 * content filter/warning settings.
 *
 * @see [User]
 */
@Entity
data class UserSettings(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,
    @OneToOne
    var selectedLayout: Layout? = null,
    var emailVisible: Boolean = false,
    var birthdayVisible: Boolean = false,
    var realNameVisible: Boolean = false,
    var maxRating: Rating = Rating.PARENTAL_GUIDANCE_13,
    @OneToMany(fetch = FetchType.EAGER)
    var showWarningForTags: MutableSet<StoryTag> = mutableSetOf(),
    var shareButtonsVisible: Boolean = true,
    var showEntireStories: Boolean = false,
    var selectedFontFamily: FontFamily = FontFamily.SANS,
    var storyBannersVisible: Boolean = true,
    var selectedFontSize: FontSize = FontSize.MEDIUM
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserSettings

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id, selectedLayout = $selectedLayout, " +
                "emailVisible = $emailVisible, birthdayVisible = $birthdayVisible, " +
                "realNameVisible = $realNameVisible, maxRating = $maxRating, " +
                "shareButtonsVisible = $shareButtonsVisible, showEntireStories = $showEntireStories, " +
                "selectedFontFamily = $selectedFontFamily, storyBannersVisible = $storyBannersVisible, " +
                "selectedFontSize = $selectedFontSize)"
    }
}
