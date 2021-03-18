package com.mercadolivro.controller.response

import com.mercadolivro.enums.Profiles
import javax.persistence.*

data class CustomerResponse (
    var id: Int?,
    var name: String,
    var email: String
)
