package org.darkSolace.muse.testUtil

import org.darkSolace.muse.lastSeen.repository.LastSeenRepository
import org.darkSolace.muse.mail.repository.MailQueueRepository
import org.darkSolace.muse.news.repository.NewsCommentRepository
import org.darkSolace.muse.news.repository.NewsRepository
import org.darkSolace.muse.privMessages.repository.PrivateMessageRepository
import org.darkSolace.muse.story.repository.ChapterCommentRepository
import org.darkSolace.muse.story.repository.ChapterRepository
import org.darkSolace.muse.story.repository.StoryRepository
import org.darkSolace.muse.user.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DBClearer {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userSettingsRepository: UserSettingsRepository

    @Autowired
    lateinit var avatarRepository: AvatarRepository

    @Autowired
    lateinit var suspensionHistoryRepository: SuspensionHistoryRepository

    @Autowired
    lateinit var lastSeenRepository: LastSeenRepository

    @Autowired
    lateinit var mailQueueRepository: MailQueueRepository

    @Autowired
    lateinit var passwordResetRequestRepository: PasswordResetRequestRepository

    @Autowired
    lateinit var privateMessageRepository: PrivateMessageRepository

    @Autowired
    lateinit var newsRepository: NewsRepository

    @Autowired
    lateinit var newsCommentRepository: NewsCommentRepository

    @Autowired
    lateinit var storyRepository: StoryRepository

    @Autowired
    lateinit var chapterRepository: ChapterRepository

    @Autowired
    lateinit var chapterCommentRepository: ChapterCommentRepository

    fun clearAll() {
        passwordResetRequestRepository.deleteAll()
        mailQueueRepository.deleteAll()
        storyRepository.deleteAll()
        chapterRepository.deleteAll()
        chapterCommentRepository.deleteAll()
        newsRepository.deleteAll()
        newsCommentRepository.deleteAll()
        privateMessageRepository.deleteAll()
        lastSeenRepository.deleteAll()
        suspensionHistoryRepository.deleteAll()
        userRepository.deleteAll()
        userSettingsRepository.deleteAll()
        avatarRepository.deleteAll()
    }
}
