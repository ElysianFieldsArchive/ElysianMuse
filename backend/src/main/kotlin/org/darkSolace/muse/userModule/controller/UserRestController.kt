package org.darkSolace.muse.userModule.controller

import org.darkSolace.muse.securityModule.model.UserDetails
import org.darkSolace.muse.userModule.model.SuspensionHistoryEntry
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.model.UserTag
import org.darkSolace.muse.userModule.service.UserRoleService
import org.darkSolace.muse.userModule.service.UserService
import org.darkSolace.muse.userModule.service.UserTagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.web.bind.annotation.*

/**
 * RestController defining endpoints regarding all user activity
 * Subject to change
 */
@RestController
@RequestMapping("/api/user")
class UserRestController(
    @Autowired val userService: UserService,
    @Autowired val userRoleService: UserRoleService,
    @Autowired val userTagService: UserTagService,
) {
    /**
     * Retrieves a user by its id. Listens on /api/user/{id}.
     *
     * @sample `curl localhost:8080/api/user/5`
     * @param id the user id
     * @return the retrieved [User] or `null`
     */
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long, authentication: Authentication?): User? {
        val user = userService.getById(id)
        return if ((authentication?.principal as UserDetails?)?.user?.id == id)
            user
        else user?.toPublicUser()
    }

    /**
     * Retrieves all users. Listens on /api/user/all.
     *
     * @sample `curl localhost:8080/api/user/all`
     * @return a List of [User]s - might be empty
     */
    @GetMapping("/all")
    fun getAllUsers(authentication: Authentication?): List<User> =
        userService.getAll()
            .map { user ->
                if ((authentication?.principal as UserDetails?)?.user?.id == user.id) user
                else user.toPublicUser()
            }

    /**
     * Deletes a user identified by its id. Listens on /api/user/{id} for DELETE requests.
     * You need the [org.darkSolace.muse.userModule.model.Role.ADMINISTRATOR] role to access this endpoint.
     *
     * @sample `curl -X DELETE -H "Authorization: [...]" localhost:8080/api/user/5`
     * @param id the user id
     * @return HTTP 200 on success, HTTP 401 otherwise
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'MODERATOR', 'ADMINISTRATOR')")
    fun deleteUserById(@PathVariable id: Long, authentication: Authentication?): ResponseEntity<*> {
        if ((authentication?.principal as UserDetails?)?.user?.id == id) {
            // user trys deletes himself (can be of role MEMBER, MODERATOR or ADMINISTRATOR)
            userService.deleteUser(id)
        } else
            if ((authentication?.principal as UserDetails?)
                    ?.authorities
                    ?.contains(SimpleGrantedAuthority("ADMINISTRATOR")) == true
            ) {
                // user is deleted by an ADMINISTRATOR
                userService.deleteUser(id)
            } else {
                return ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
            }

        return ResponseEntity<Unit>(HttpStatus.OK)
    }

    /**
     * Suspends a user identified by its id. Listens on /api/user/{id} for POST requests.
     * You need the [org.darkSolace.muse.userModule.model.Role.ADMINISTRATOR] or
     * [org.darkSolace.muse.userModule.model.Role.MODERATOR] role to access this endpoint.
     *
     * @sample `curl -X POST -H "Authorization: [...]" localhost:8080/api/user/suspend/5`
     * @param id the user id
     * @return HTTP 200 or 400
     */
    @PostMapping("/suspend/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'MODERATOR')")
    fun suspendUserById(@PathVariable id: Long): ResponseEntity<*> {
        val user = userRoleService.suspendUser(id)
        return if (user == null) ResponseEntity<Unit>(HttpStatus.BAD_REQUEST) else ResponseEntity<Unit>(HttpStatus.OK)
    }

    /**
     * Confirms a [SuspensionHistoryEntry], identified by its confirmation code.
     *
     * @sample `curl -X POST localhost:8080/api/user/suspend/confirm/9df2cc31-f733-4daa-8277-d3c0afdb1a5a`
     * @param confirmationCode the confirmation code
     * @return HTTP 200 on successful confirmation, HTTP 400 otherwise
     */
    @PostMapping("/suspend/confirm/{confirmationCode}")
    fun confirmSuspension(@PathVariable confirmationCode: String): ResponseEntity<*> {
        val success = userRoleService.confirmSuspension(confirmationCode)
        return if (success)
            ResponseEntity<Unit>(HttpStatus.OK)
        else
            ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
    }

    /**
     * Retrieves the suspension history for a given user, identified by its id.
     * You need the [org.darkSolace.muse.userModule.model.Role.ADMINISTRATOR] or
     * [org.darkSolace.muse.userModule.model.Role.MODERATOR] role to access this endpoint.
     *
     * @sample `curl -X POST -H "Authorization: [...]" localhost:8080/api/user/suspend/history/5`
     * @param id the user id
     */
    @GetMapping("/suspend/history/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATION', 'MODERATOR')")
    fun getSuspensionHistory(@PathVariable id: Long): List<SuspensionHistoryEntry> {
        return userRoleService.getSuspensionHistory(id)
    }

    /**
     * Retrieves all currently suspended users
     * You need the [org.darkSolace.muse.userModule.model.Role.ADMINISTRATOR] or
     * [org.darkSolace.muse.userModule.model.Role.MODERATOR] role to access this endpoint.
     *
     * @sample `curl -H "Authorization: [...]" localhost:8080/api/user/suspend/all`
     * @return List of the suspended [User]s
     */
    @GetMapping("/suspend/all")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATION', 'MODERATOR')")
    fun getAllCurrentlySuspended(): List<User> {
        return userRoleService.getAllCurrentlySuspendedUsers()
    }

    /**
     * Adds a [UserTag] to a [User].
     * A [User] can add [UserTag]s to itself.
     * To add tags to a different user either the [org.darkSolace.muse.userModule.model.Role] of
     * [org.darkSolace.muse.userModule.model.Role.ADMINISTRATOR] or
     * [org.darkSolace.muse.userModule.model.Role.MODERATOR] is required.
     *
     * @sample `curl -X PUT -H "Authorization: [...]" localhost:8080/api/user/5/ARTIST`
     * @param user the id of the [User] to add a [UserTag] to
     * @param tag the [UserTag] to add
     */
    @PutMapping("/{user}/tag/{tag}")
    fun addTagToUser(
        @PathVariable user: User?,
        @PathVariable tag: UserTag,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        if (user == null) return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        val userDetails = (authentication?.principal as UserDetails?)
        return if (userDetails?.user == user) {
            // user trys deletes himself (can be of role MEMBER, MODERATOR or ADMINISTRATOR)
            userTagService.addTagToUser(user, tag)
            ResponseEntity<Unit>(HttpStatus.OK)
        } else
            if (userDetails
                    ?.authorities
                    ?.contains(SimpleGrantedAuthority("ADMINISTRATOR")) == true ||
                userDetails
                    ?.authorities
                    ?.contains(SimpleGrantedAuthority("MODERATOR")) == true
            ) {
                // tag is added by an ADMIN or MOD
                userTagService.addTagToUser(user, tag)
                ResponseEntity<Unit>(HttpStatus.OK)
            } else {
                ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
            }
    }

    /**
     * Removes a [UserTag] from a [User].
     * A [User] can remove [UserTag]s from itself.
     * To remove tags from a different user either the [org.darkSolace.muse.userModule.model.Role] of
     * [org.darkSolace.muse.userModule.model.Role.ADMINISTRATOR] or
     * [org.darkSolace.muse.userModule.model.Role.MODERATOR] is required.
     *
     * @sample `curl -X DELETE -H "Authorization: [...]" localhost:8080/api/user/5/ARTIST`
     * @param user the id of the [User] to remove a [UserTag] from
     * @param tag the [UserTag] to remove
     */
    @DeleteMapping("/{user}/tag/{tag}")
    fun removeTagFromUser(
        @PathVariable user: User?,
        @PathVariable tag: UserTag,
        authentication: Authentication?
    ): ResponseEntity<Unit> {
        if (user == null) return ResponseEntity<Unit>(HttpStatus.BAD_REQUEST)
        val userDetails = (authentication?.principal as UserDetails?)
        if (userDetails?.user == user) {
            // user trys deletes himself (can be of role MEMBER, MODERATOR or ADMINISTRATOR)
            userTagService.removeTagFromUser(user, tag)
        } else
            if (userDetails
                    ?.authorities
                    ?.contains(SimpleGrantedAuthority("ADMINISTRATOR")) == true ||
                userDetails
                    ?.authorities
                    ?.contains(SimpleGrantedAuthority("MODERATOR")) == true
            ) {
                // user is deleted by an ADMINISTRATOR
                userTagService.removeTagFromUser(user, tag)
            } else {
                return ResponseEntity<Unit>(HttpStatus.UNAUTHORIZED)
            }
        return ResponseEntity<Unit>(HttpStatus.OK)
    }
}
