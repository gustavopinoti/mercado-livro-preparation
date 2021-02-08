package com.mercadolivro.extensions

import com.mercadolivro.controller.request.CustomerPostRequest
import com.mercadolivro.controller.request.CustomerPutRequest
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