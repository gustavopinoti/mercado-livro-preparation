package com.mercadolivro.security

import com.mercadolivro.model.CustomerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserSecurity(private val user: CustomerModel) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf() //TODO adicionar authorities em customer
    override fun isEnabled(): Boolean = true
    override fun getUsername(): String = user.email
    override fun isCredentialsNonExpired(): Boolean = true
    override fun getPassword(): String = "" //TODO adicionar senha em customer
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
}
