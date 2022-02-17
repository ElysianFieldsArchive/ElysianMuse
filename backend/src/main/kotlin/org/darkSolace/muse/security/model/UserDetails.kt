package org.darkSolace.muse.security.model

import org.darkSolace.muse.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


/**
 * [UserDetails] model to pass required information to the [org.darkSolace.muse.security.service.AuthTokenFilter]
 */
class UserDetails(val user: User?) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        if (user == null) return emptyList()
        return listOf(SimpleGrantedAuthority(user.role.name))
    }

    override fun getPassword() = user?.password ?: ""

    override fun getUsername() = user?.username ?: ""

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
