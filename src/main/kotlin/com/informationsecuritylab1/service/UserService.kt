package com.informationsecuritylab1.service

import com.informationsecuritylab1.entity.User
import com.informationsecuritylab1.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val repo: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun register(username: String, rawPassword: String): User {
        if (repo.findByUsername(username).isPresent) {
            throw IllegalArgumentException("User exists")
        }
        val hash = passwordEncoder.encode(rawPassword)
        val user = User(username = username, passwordHash = hash)
        return repo.save(user)
    }

    fun authenticate(username: String, rawPassword: String): Optional<User> {
        val userOpt = repo.findByUsername(username)
        if (userOpt.isEmpty) return Optional.empty()
        val u = userOpt.get()
        return if (passwordEncoder.matches(rawPassword, u.passwordHash)) Optional.of(u) else Optional.empty()
    }
}
