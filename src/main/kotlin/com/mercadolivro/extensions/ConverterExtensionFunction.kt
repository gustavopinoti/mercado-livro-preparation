package com.mercadolivro.extensions

import com.mercadolivro.controller.request.BookPostRequest
import com.mercadolivro.controller.request.BookPutRequest
import com.mercadolivro.controller.request.CustomerPostRequest
import com.mercadolivro.controller.request.CustomerPutRequest
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel

fun CustomerPostRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(
        name = this.name!!,
        email = this.email!!
    )
}

fun CustomerPutRequest.toCustomerModel(id:Int): CustomerModel {
    return CustomerModel(
        id = id,
        name = this.name!!,
        email = this.email!!
    )
}

fun BookPostRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(
        name = this.name,
        price = this.price,
        customer = customer,
        status = BookStatus.ATIVO
    )
}

fun BookPutRequest.toBookModel(id: Int): BookModel {
    return BookModel(
        id = id,
        name = this.name,
        price = this.price
    )
}