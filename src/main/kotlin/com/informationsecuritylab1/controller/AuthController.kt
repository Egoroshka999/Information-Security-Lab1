package com.informationsecuritylab1.controller

import com.informationsecuritylab1.config.JwtUtil
import com.informationsecuritylab1.dto.LoginRequest
import com.informationsecuritylab1.dto.LoginResponse
import com.informationsecuritylab1.dto.RegisterRequest
import com.informationsecuritylab1.service.UserService
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@SuppressFBWarnings(
    value = ["SA_LOCAL_SELF_ASSIGNMENT"],
    justification = "False positive: Kotlin bytecode misdetected as self-assignment"
)
@RestController
@RequestMapping("/auth")
class AuthController(private val userService: UserService, private val jwtUtil: JwtUtil) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody req: RegisterRequest): ResponseEntity<Any> {
        return try {
            val user = userService.register(req.username, req.password)
            ResponseEntity.ok(mapOf("id" to user.id, "username" to user.username))
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody req: LoginRequest): ResponseEntity<Any> {
        val userOpt = userService.authenticate(req.username, req.password)
        return if (userOpt.isPresent) {
            val token = jwtUtil.generateToken(userOpt.get().username)
            ResponseEntity.ok(LoginResponse(token))
        } else {
            ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
        }
    }
}
