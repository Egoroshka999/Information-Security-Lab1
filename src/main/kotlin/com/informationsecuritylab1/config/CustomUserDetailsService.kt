package com.informationsecuritylab1.config

import com.informationsecuritylab1.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val repo: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repo.findByUsername(username).orElseThrow { UsernameNotFoundException("User not found") }
        return org.springframework.security.core.userdetails.User(user.username, user.passwordHash, listOf(SimpleGrantedAuthority("ROLE_${user.role}")))
    }
}
