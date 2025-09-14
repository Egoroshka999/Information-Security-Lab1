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
    fun getData(): Any {
        // В качестве примера возвращаем список пользователей (username) — но экранируем их для XSS
        val users = userRepo.findAll().map { u ->
            mapOf(
                "id" to u.id,
                // simple XSS protection: escape username before returning
                "username" to HtmlUtils.htmlEscape(u.username)
            )
        }
        return mapOf("users" to users)
    }
}
