package org.darkSolace.muse.securityModule.configuration

import org.darkSolace.muse.securityModule.service.AuthEntryPointJwt
import org.darkSolace.muse.securityModule.service.AuthTokenFilter
import org.darkSolace.muse.securityModule.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


/**
 * Configuration class to configure authentication via JWT
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
    @Autowired val userDetailsService: UserDetailsService,
    @Autowired val unauthorizedHandler: AuthEntryPointJwt
) : WebSecurityConfigurerAdapter() {

    /**
     * Configures the [AuthenticationManager], via [AuthenticationManagerBuilder],
     * to use [UserDetailsService] and [BCryptPasswordEncoder]
     */
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)?.passwordEncoder(passwordEncoder())
    }

    /**
     * Use the default [AuthenticationManager]
     */
    @Bean
    override fun authenticationManager(): AuthenticationManager = super.authenticationManager()

    /**
     * Sets the password encoder to [BCryptPasswordEncoder]
     */
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    /**
     * The actual [WebSecurityConfiguration]. Defimes [AuthEntryPointJwt] as handler for unauthorized requests
     * and allows access to all API endpoints.
     *
     * Access to API endpoints is configured in each RestController via `@PreAuthorize`
     */
    override fun configure(http: HttpSecurity?) {
        if (http == null) return

        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            //allow access to all api endpoints, permissions handled in the controller
            .antMatchers("/api/**").permitAll()
            .anyRequest().authenticated()

        http.addFilterBefore(AuthTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }
}
