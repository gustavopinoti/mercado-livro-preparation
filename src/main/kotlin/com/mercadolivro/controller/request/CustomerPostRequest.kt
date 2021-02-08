package com.mercadolivro.controller.request

import javax.validation.constraints.NotEmpty

data class CustomerPostRequest (
    @field:NotEmpty
    var name: String?,

    @field:NotEmpty
    var email: String?
)