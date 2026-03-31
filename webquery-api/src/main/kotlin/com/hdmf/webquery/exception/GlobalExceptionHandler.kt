package com.hdmf.webquery.exception

import com.hdmf.webquery.helloworld.exception.HelloWorldNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HelloWorldNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleHelloWorldNotFoundException(e: HelloWorldNotFoundException): ErrorResponse =
        ErrorResponse(code = "HELLO_WORLD_NOT_FOUND", message = e.message ?: "HelloWorld를 찾을 수 없습니다")

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorResponse =
        ErrorResponse(
            code = "INVALID_REQUEST",
            message = "잘못된 요청입니다",
            errors =
                e.bindingResult.fieldErrors.map {
                    ErrorResponse.FieldError(field = it.field, message = it.defaultMessage ?: "")
                },
        )

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBusinessException(e: BusinessException): ErrorResponse =
        ErrorResponse(code = "BUSINESS_ERROR", message = e.message ?: "비즈니스 오류가 발생했습니다")

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): ErrorResponse =
        ErrorResponse(code = "INTERNAL_SERVER_ERROR", message = "서버 오류가 발생했습니다")
}
