package org.darkSolace.muse.configuration

import org.darkSolace.muse.lastSeen.service.LastSeenFilter
import org.darkSolace.muse.security.service.AuthEntryPointJwt
import org.darkSolace.muse.security.service.AuthTokenFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


/**
 * Configuration class to configure authentication via JWT
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
    @Autowired val unauthorizedHandler: AuthEntryPointJwt,
    @Autowired val authTokenFilter: AuthTokenFilter,
    @Autowired val lastSeenFilter: LastSeenFilter,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterAfter(lastSeenFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
