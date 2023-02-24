package org.darkSolace.muse.lastSeen.controller

import org.darkSolace.muse.lastSeen.service.LastSeenService
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.dto.UserIdNameDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/lastSeen")
class LastSeenController(@Autowired val lastSeenService: LastSeenService) {
    /**
     * Retrieves all public profiles of currently active logged-in [User]s
     *
     * @sample `curl localhost:8000/api/stats/online`
     *
     * @return list of active logged-in users
     */
    @GetMapping("/online")
    fun getOnlineUsers(): List<UserIdNameDTO> = UserIdNameDTO.fromList(lastSeenService.getOnlineUsers())

    /**
     * Retrieves number of currently active visitors (logged-in users and visitors)
     *
     * @sample `curl localhost:8000/api/stats/online/count`
     *
     * @return number of currently active visitors
     */
    @GetMapping("/online/count")
    fun getOnlineUsersCount(): Long = lastSeenService.getOnlineUserCount()
}
