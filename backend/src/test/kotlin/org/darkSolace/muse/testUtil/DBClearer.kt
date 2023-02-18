package org.darkSolace.muse.testUtil

import org.darkSolace.muse.lastSeen.repository.LastSeenRepository
import org.darkSolace.muse.mail.repository.MailQueueRepository
import org.darkSolace.muse.privMessages.repository.PrivateMessageRepository
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

    fun clearAll() {
        privateMessageRepository.deleteAll()
        passwordResetRequestRepository.deleteAll()
        mailQueueRepository.deleteAll()
        lastSeenRepository.deleteAll()
        suspensionHistoryRepository.deleteAll()
        userRepository.deleteAll()
        userSettingsRepository.deleteAll()
        avatarRepository.deleteAll()
    }
}
