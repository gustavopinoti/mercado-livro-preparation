package com.mercadolivro.exception

import org.springframework.http.HttpStatus

class BadRequestException(override val message: String) : Exception(message) {

    val code: Int = HttpStatus.BAD_REQUEST.value()

}
