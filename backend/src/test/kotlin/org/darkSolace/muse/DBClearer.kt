package org.darkSolace.muse

import org.darkSolace.muse.userModule.repository.AvatarRepository
import org.darkSolace.muse.userModule.repository.SuspensionHistoryRepository
import org.darkSolace.muse.userModule.repository.UserRepository
import org.darkSolace.muse.userModule.repository.UserSettingsRepository
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
    val suspensionHistoryRepository: SuspensionHistoryRepository
) {
    fun clearAll() {
        suspensionHistoryRepository.deleteAll()
        userRepository.deleteAll()
        userSettingsRepository.deleteAll()
        avatarRepository.deleteAll()
    }
}
