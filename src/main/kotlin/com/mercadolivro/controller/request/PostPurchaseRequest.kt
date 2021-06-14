package com.mercadolivro.controller.request

class PostPurchaseRequest (

    val customerId: Int,

    val bookIds: Set<Int>
)
