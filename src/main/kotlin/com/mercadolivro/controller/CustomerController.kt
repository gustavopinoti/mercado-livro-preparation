package com.mercadolivro.controller

import com.mercadolivro.controller.request.CustomerPostRequest
import com.mercadolivro.controller.request.CustomerPutRequest
import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.extensions.toCustomerModel
import com.mercadolivro.extensions.toCustomerResponse
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customer")
class CustomerController(val customerService: CustomerService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody customer: CustomerPostRequest) {
        customerService.create(customer.toCustomerModel())
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<CustomerResponse> {
        return customerService.findAll().map { it.toCustomerResponse() }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): CustomerResponse {
        return customerService.findById(id).toCustomerResponse()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Int) {
        customerService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable("id") id: Int, @Valid @RequestBody customer: CustomerPutRequest) {
        customerService.update(customer.toCustomerModel(id))
    }

}
