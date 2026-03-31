package com.hdmf.webquery.helloworld.dto

import com.hdmf.webquery.helloworld.HelloWorld

data class HelloWorldResponse(
    val id: Long,
    val message: String,
)

fun HelloWorld.toResponse() = HelloWorldResponse(id = id, message = message)
