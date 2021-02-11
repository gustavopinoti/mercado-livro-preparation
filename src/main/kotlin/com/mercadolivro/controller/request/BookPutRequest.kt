package com.mercadolivro.controller.request

import java.math.BigDecimal

data class BookPutRequest (

    var name: String,

    var price: BigDecimal
)
