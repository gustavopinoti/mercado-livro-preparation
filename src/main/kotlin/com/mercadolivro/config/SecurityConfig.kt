package com.mercadolivro.config

import com.mercadolivro.enums.Profiles
import com.mercadolivro.exception.RestAccessDeniedHandler
import com.mercadolivro.exception.RestAuthenticationEntryPoint
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.JwtAuthenticationFilter
import com.mercadolivro.security.JwtAuthorizationFilter
import com.mercadolivro.security.JwtUtil
import com.mercadolivro.service.UserDetailsServiceImp
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsServiceImp,
    private val customerRepository: CustomerRepository,
    private val entryPoint: RestAuthenticationEntryPoint,
    private val deniedHandler: RestAccessDeniedHandler
): WebSecurityConfigurerAdapter() {

    private val PUBLIC_MATCHERS = arrayOf<String>()

    private val PUBLIC_MATCHERS_POST = arrayOf(
            "/customer"
    )

    private val PUBLIC_MATCHERS_GET = arrayOf<String>()

    private val ADMIN_MATCHERS = arrayOf(
            "/admin/**"
    )

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder())
    }

    @Throws(java.lang.Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
                "/swagger-ui.html", "/webjars/**")
    }


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
        http.authorizeRequests()
                .antMatchers(*PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.POST, *PUBLIC_MATCHERS_POST).permitAll()
                .antMatchers(HttpMethod.GET, *PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(*ADMIN_MATCHERS).hasAuthority(Profiles.ADMIN.description)
                .anyRequest().authenticated()
        http.addFilter(JwtAuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))
        http.addFilter(JwtAuthorizationFilter(authenticationManager(), userDetailsService, jwtUtil))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.exceptionHandling().accessDeniedHandler(deniedHandler).authenticationEntryPoint(entryPoint);

    }

    @Bean
    fun corsConfigurationSource(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}