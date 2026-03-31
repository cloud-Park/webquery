package com.hdmf.webquery.helloworld

import com.hdmf.webquery.helloworld.dto.HelloWorldRequest
import com.hdmf.webquery.helloworld.dto.HelloWorldResponse
import com.hdmf.webquery.helloworld.dto.toResponse
import com.hdmf.webquery.helloworld.exception.HelloWorldNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class HelloWorldService(
    private val helloWorldRepository: HelloWorldRepository,
) {
    fun findAll(): List<HelloWorldResponse> = helloWorldRepository.findAll().map { it.toResponse() }

    fun findById(id: Long): HelloWorldResponse =
        helloWorldRepository.findByIdOrNull(id)?.toResponse()
            ?: throw HelloWorldNotFoundException(id)

    @Transactional
    fun createHelloWorld(request: HelloWorldRequest): HelloWorldResponse =
        helloWorldRepository.save(HelloWorld(message = request.message)).toResponse()

    @Transactional
    fun updateHelloWorld(
        id: Long,
        request: HelloWorldRequest,
    ): HelloWorldResponse {
        val entity =
            helloWorldRepository.findByIdOrNull(id)
                ?: throw HelloWorldNotFoundException(id)
        entity.update(request.message)
        return entity.toResponse()
    }

    @Transactional
    fun deleteHelloWorld(id: Long) {
        if (!helloWorldRepository.existsById(id)) throw HelloWorldNotFoundException(id)
        helloWorldRepository.deleteById(id)
    }
}
