package com.hdmf.webquery.helloworld

import com.hdmf.webquery.helloworld.dto.HelloWorldRequest
import com.hdmf.webquery.helloworld.dto.HelloWorldResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
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

@Tag(name = "HelloWorld")
@RestController
@RequestMapping("/api/v1/hello")
class HelloWorldController(
    private val helloWorldService: HelloWorldService,
) {
    @Operation(summary = "전체 조회")
    @GetMapping
    fun findAll(): List<HelloWorldResponse> = helloWorldService.findAll()

    @Operation(summary = "단건 조회")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "404", description = "존재하지 않는 ID"),
    )
    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long,
    ): HelloWorldResponse = helloWorldService.findById(id)

    @Operation(summary = "생성")
    @ApiResponses(
        ApiResponse(responseCode = "201"),
        ApiResponse(responseCode = "400", description = "유효하지 않은 요청"),
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createHelloWorld(
        @RequestBody @Valid request: HelloWorldRequest,
    ): HelloWorldResponse = helloWorldService.createHelloWorld(request)

    @Operation(summary = "수정")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", description = "유효하지 않은 요청"),
        ApiResponse(responseCode = "404", description = "존재하지 않는 ID"),
    )
    @PutMapping("/{id}")
    fun updateHelloWorld(
        @PathVariable id: Long,
        @RequestBody @Valid request: HelloWorldRequest,
    ): HelloWorldResponse = helloWorldService.updateHelloWorld(id, request)

    @Operation(summary = "삭제")
    @ApiResponses(
        ApiResponse(responseCode = "204"),
        ApiResponse(responseCode = "404", description = "존재하지 않는 ID"),
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteHelloWorld(
        @PathVariable id: Long,
    ) = helloWorldService.deleteHelloWorld(id)
}
