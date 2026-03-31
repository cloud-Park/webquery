package com.hdmf.webquery.config

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class RequestLoggingAspect {
    private val log = LoggerFactory.getLogger(RequestLoggingAspect::class.java)

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    fun logRequest(joinPoint: ProceedingJoinPoint): Any? {
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request = attributes.request
        val method = request.method
        val uri = request.requestURI

        log.info(">>> {} {}", method, uri)
        val start = System.currentTimeMillis()

        return try {
            val result = joinPoint.proceed()
            val duration = System.currentTimeMillis() - start
            log.info("<<< {} {} - {}ms", method, uri, duration)
            result
        } catch (e: Exception) {
            val duration = System.currentTimeMillis() - start
            log.warn("<<< {} {} - {}ms [{}]", method, uri, duration, e.message)
            throw e
        }
    }
}
