package org.darkSolace.muse

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
    val avatarRepository: UserRepository,
) {
    fun clearAll() {
        avatarRepository.deleteAll()
        userSettingsRepository.deleteAll()
        userRepository.deleteAll()
    }
}
