package com.mercadolivro.exception

import org.springframework.http.HttpStatus

class NotFoundException(override val message: String) : Exception(message) {

    val code: Int = HttpStatus.NOT_FOUND.value()

}
