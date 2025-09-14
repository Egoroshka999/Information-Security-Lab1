package com.informationsecuritylab1.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true, nullable = false)
    val username: String,
    @Column(nullable = false)
    var passwordHash: String,
    @Column(nullable = false)
    var role: String = "USER"
)
