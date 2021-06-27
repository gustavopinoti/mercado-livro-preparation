package com.mercadolivro.service

import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.UserSecurity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImp(
    private val repo: CustomerRepository
): UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = repo.findByEmail(email).orElseThrow { UsernameNotFoundException(email) }
        return UserSecurity(user)
    }
}