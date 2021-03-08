package com.mercadolivro.service

import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
        val customerRepository: CustomerRepository,
        val bookService: BookService) {


    fun create(customer: CustomerModel) {
        customerRepository.save(customer)
    }

    fun findAll(): List<CustomerModel> {
        return customerRepository.findAll().toList()
    }

    fun delete(id: Int) {
        val customer = findById(id)
        bookService.deleteByCustomer(customer)
        customerRepository.deleteById(id)
    }

    fun update(customer: CustomerModel) {
        if(!customerRepository.existsById(customer.id!!)) {
            throw BadRequestException("Customer [${customer.id}] Not Exists")
        }

        customerRepository.save(customer)
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }

    fun findById(customerId: Int): CustomerModel = customerRepository.findById(customerId)
            .orElseThrow { NotFoundException("Customer [${customerId}] Not Exists") }

}
