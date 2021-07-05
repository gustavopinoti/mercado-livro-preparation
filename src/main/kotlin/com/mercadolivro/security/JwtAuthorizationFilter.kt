package com.mercadolivro.security

import com.mercadolivro.exception.AuthenticationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws
import com.mercadolivro.enums.Errors.ML0001


class JwtAuthorizationFilter(
        authenticationManager: AuthenticationManager,
        private val userDetailService: UserDetailsService,
        private val jwtUtil: JwtUtil) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")
        if (header != null && header.startsWith("Bearer ")) {
            val auth = getAuthentication(header.substring(7))
            SecurityContextHolder.getContext().authentication = auth
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        if (!jwtUtil.isValidToken(token)) {
            throw AuthenticationException(ML0001.message, ML0001.code)
        }
        val username: String = jwtUtil.getUsername(token)!!
        val user = userDetailService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

}