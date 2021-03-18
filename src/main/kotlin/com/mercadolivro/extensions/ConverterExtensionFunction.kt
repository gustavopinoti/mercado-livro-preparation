package com.mercadolivro.extensions

import com.mercadolivro.controller.request.BookPostRequest
import com.mercadolivro.controller.request.BookPutRequest
import com.mercadolivro.controller.request.CustomerPostRequest
import com.mercadolivro.controller.request.CustomerPutRequest
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.controller.response.CustomerResponse
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

fun BookPutRequest.toBookModel(previousValue: BookModel): BookModel {
    return BookModel(
        id = previousValue.id,
        name = this.name ?: previousValue.name,
        price = this.price ?: previousValue.price,
        customer = previousValue.customer,
        status = previousValue.status
    )
}

fun CustomerModel.toCustomerResponse(): CustomerResponse {
    return CustomerResponse(
        id = this.id,
        name = this.name,
        email = this.email
    )
}

fun BookModel.toBookResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer?.toCustomerResponse(),
        status = this.status,
        soldAt = this.soldAt
    )
}