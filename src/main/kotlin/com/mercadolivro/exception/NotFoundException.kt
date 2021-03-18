package com.mercadolivro.exception

import com.mercadolivro.enums.Errors

class NotFoundException(override val message: String, val errorCode: String) : Exception() {

}
