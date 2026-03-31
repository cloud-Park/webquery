package com.hdmf.webquery.helloworld

import com.hdmf.webquery.helloworld.dto.HelloWorldRequest
import com.hdmf.webquery.helloworld.dto.HelloWorldResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/hello")
class HelloWorldController(
    private val helloWorldService: HelloWorldService,
) {
    @GetMapping
    fun findAll(): List<HelloWorldResponse> = helloWorldService.findAll()

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long,
    ): HelloWorldResponse = helloWorldService.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createHelloWorld(
        @RequestBody @Valid request: HelloWorldRequest,
    ): HelloWorldResponse = helloWorldService.createHelloWorld(request)

    @PutMapping("/{id}")
    fun updateHelloWorld(
        @PathVariable id: Long,
        @RequestBody @Valid request: HelloWorldRequest,
    ): HelloWorldResponse = helloWorldService.updateHelloWorld(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteHelloWorld(
        @PathVariable id: Long,
    ) = helloWorldService.deleteHelloWorld(id)
}
