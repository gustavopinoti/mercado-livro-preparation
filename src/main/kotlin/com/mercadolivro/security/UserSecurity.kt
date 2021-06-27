package com.mercadolivro.security

import com.mercadolivro.model.CustomerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserSecurity(private val user: CustomerModel) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = user.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()
    override fun isEnabled(): Boolean = true
    override fun getUsername(): String = user.email
    override fun isCredentialsNonExpired(): Boolean = true
    override fun getPassword(): String = user.password
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
}
