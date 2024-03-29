package com.mercadolivro.config

import com.mercadolivro.security.JwtAuthenticationFilter
import com.mercadolivro.security.JwtAuthorizationFilter
import com.mercadolivro.security.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import kotlin.jvm.Throws

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtUtil: JwtUtil
): WebSecurityConfigurerAdapter() {

    private val PUBLIC_MATCHERS = arrayOf<String>()

    private val PUBLIC_MATCHERS_POST = arrayOf<String>()

    private val PUBLIC_MATCHERS_GET = arrayOf<String>()

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder())
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
                .anyRequest().permitAll()
        http.addFilter(JwtAuthenticationFilter(authenticationManager(), jwtUtil))
        http.addFilter(JwtAuthorizationFilter(authenticationManager(), userDetailsService(), jwtUtil))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}