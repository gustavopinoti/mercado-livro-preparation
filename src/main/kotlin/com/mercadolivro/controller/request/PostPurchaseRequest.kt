package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias

class PostPurchaseRequest (

    @JsonAlias("customer_id")
    val customerId: Int,

    @JsonAlias("book_ids")
    val bookIds: Set<Int>
)
