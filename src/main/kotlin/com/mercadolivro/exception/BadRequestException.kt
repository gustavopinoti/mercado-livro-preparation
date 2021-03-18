package com.mercadolivro.exception

import com.mercadolivro.enums.Errors
import org.springframework.http.HttpStatus

class BadRequestException(override val message: String, val errorCode: String) : Exception(message) {


}
