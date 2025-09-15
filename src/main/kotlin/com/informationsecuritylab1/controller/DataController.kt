package com.informationsecuritylab1.controller

import com.informationsecuritylab1.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.HtmlUtils

@RestController
@RequestMapping("/api")
class DataController(private val userRepo: UserRepository) {

    @GetMapping("/data")
    fun getData(): Map<String, List<Map<String, Any>>> {
        // В качестве примера возвращаем список пользователей (username) — но экранируем их для XSS
        val users: List<Map<String, Any>> = userRepo.findAll().toList().map { u ->
            mapOf(
                "id" to u.id,
                "username" to HtmlUtils.htmlEscape(u.username)
            )
        }
        return mapOf("users" to users)
    }
}
