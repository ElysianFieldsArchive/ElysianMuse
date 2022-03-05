package org.darkSolace.muse.testUtil

import org.darkSolace.muse.lastSeen.repository.LastSeenRepository
import org.darkSolace.muse.user.repository.AvatarRepository
import org.darkSolace.muse.user.repository.SuspensionHistoryRepository
import org.darkSolace.muse.user.repository.UserRepository
import org.darkSolace.muse.user.repository.UserSettingsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DBClearer(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val userSettingsRepository: UserSettingsRepository,
    @Autowired
    val avatarRepository: AvatarRepository,
    @Autowired
    val suspensionHistoryRepository: SuspensionHistoryRepository,
    @Autowired
    val lastSeenRepository: LastSeenRepository
) {
    fun clearAll() {
        lastSeenRepository.deleteAll()
        suspensionHistoryRepository.deleteAll()
        userRepository.deleteAll()
        userSettingsRepository.deleteAll()
        avatarRepository.deleteAll()
    }
}
