package com.mercadolivro.exception

import com.mercadolivro.controller.response.ErrorResponse
import com.mercadolivro.enums.Errors
import com.mercadolivro.model.ErrorModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorDetails = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            message = ex.message,
            errorCode = ex.errorCode
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorDetails = ErrorResponse(
            code = HttpStatus.NOT_FOUND.value(),
            message = ex.message,
            errorCode = ex.errorCode
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException, request: WebRequest?): ResponseEntity<ErrorResponse> {
        val errorDetails = ErrorResponse(
            code = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            message = HttpStatus.UNPROCESSABLE_ENTITY.reasonPhrase,
            error = ex.bindingResult.fieldErrors.map { ErrorModel(it.field, it.defaultMessage ?: "invalid") },
            errorCode = Errors.ML3000.code
        )
        return ResponseEntity(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY)
    }

}
