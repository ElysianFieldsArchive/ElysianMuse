package org.darkSolace.muse.securityModule.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/test/auth")
class AuthTestController {

    @GetMapping("/")
    fun testWithoutAuthorisation(): ResponseEntity<String> = ResponseEntity.ok().body("Test passed")

    @GetMapping("/restricted_member")
    @PreAuthorize("hasAuthority('MEMBER')")
    fun requiresMember(): ResponseEntity<String> = ResponseEntity.ok().body("Test passed")

    @GetMapping("/restricted_admin")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    fun requiresAdmin(): ResponseEntity<String> = ResponseEntity.ok().body("Test passed")

    @GetMapping("/restricted_mod")
    @PreAuthorize("hasAuthority('MODERATOR')")
    fun requiresMod(): ResponseEntity<String> = ResponseEntity.ok().body("Test passed")

    @GetMapping("/restricted_many")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMINISTRATOR')")
    fun requiresModOrAdmin(): ResponseEntity<String> = ResponseEntity.ok().body("Test passed")
}
