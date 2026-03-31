package com.hdmf.webquery.exception

data class ErrorResponse(
    val code: String,
    val message: String,
    val errors: List<FieldError>? = null,
) {
    data class FieldError(
        val field: String,
        val message: String,
    )
}
