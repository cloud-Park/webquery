package com.hdmf.webquery.helloworld.dto

import jakarta.validation.constraints.NotBlank

data class HelloWorldRequest(
    @field:NotBlank
    val message: String,
)
