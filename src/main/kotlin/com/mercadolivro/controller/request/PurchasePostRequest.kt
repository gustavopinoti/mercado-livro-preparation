package com.mercadolivro.controller.request

import org.jetbrains.annotations.NotNull
import javax.validation.constraints.Min

data class PurchasePostRequest(
    @field:NotNull
    @field:Min(0)
    val bookId: Int,

    @field:NotNull
    @field:Min(0)
    val userId: Int
)