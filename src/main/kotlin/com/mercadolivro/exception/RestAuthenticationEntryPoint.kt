package com.mercadolivro.exception

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.annotations.ApiResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.OutputStream
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.outputStream.println("{ \"error\": \"" + authenticationException.message + "\" }")
    }
}

@Component
class RestAccessDeniedHandler : AccessDeniedHandler {
    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        e: AccessDeniedException?
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.outputStream.println("{ \"error\": \"mensagem teste\" }")
    }

}