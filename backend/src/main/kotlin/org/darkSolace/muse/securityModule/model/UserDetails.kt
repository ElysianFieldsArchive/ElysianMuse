package org.darkSolace.muse.securityModule.model

import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


/**
 * [UserDetails] model to pass required information to the [org.darkSolace.muse.securityModule.service.AuthTokenFilter]
 */
class UserDetails(val user: User) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(user.role.name))
    }

    override fun getPassword() = user.password

    override fun getUsername() = user.username

    override fun isAccountNonExpired() = user.role != Role.SUSPENDED

    override fun isAccountNonLocked() = user.role != Role.SUSPENDED

    override fun isCredentialsNonExpired() = user.role != Role.SUSPENDED

    override fun isEnabled() = user.role != Role.SUSPENDED
}