package com.mercadolivro.controller.request

import com.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class CustomerPostRequest (
    @field:NotEmpty
    var name: String?,

    @field:Email
    @EmailAvailable
    var email: String?

)