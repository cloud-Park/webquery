package com.hdmf.webquery.helloworld

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "hello_world")
class HelloWorld(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var message: String,
) {
    fun update(message: String) {
        this.message = message
    }
}
