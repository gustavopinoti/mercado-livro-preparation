package com.mercadolivro.controller.response

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.exception.BadRequestException
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

data class BookResponse(

    var id: Int?,

    var name: String,

    var price: BigDecimal,

    var customer: CustomerResponse?,

    var status: BookStatus?,

    var soldAt: LocalDateTime? = null
)

