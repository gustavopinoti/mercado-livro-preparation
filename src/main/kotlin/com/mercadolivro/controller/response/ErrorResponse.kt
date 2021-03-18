package com.mercadolivro.controller.response

import com.mercadolivro.model.ErrorModel

data class ErrorResponse (
    var code: Int,
    var message: String,
    var errorCode: String,
    var error: List<ErrorModel>? = null
)
