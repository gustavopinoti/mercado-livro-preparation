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
    override fun loadUserByUsername(id: String): UserDetails {
        val user = repo.findById(id.toInt()).orElseThrow { UsernameNotFoundException(id) }
        return UserSecurity(user)
    }
}