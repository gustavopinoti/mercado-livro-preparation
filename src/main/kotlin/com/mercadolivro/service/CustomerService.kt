package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.enums.Profiles
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val bookService: BookService,
    val bCrypt: BCryptPasswordEncoder
) {

    fun create(customer: CustomerModel) {
        val newCustomer = customer.copy(
                roles = mutableSetOf(Profiles.CUSTOMER),
                password = bCrypt.encode(customer.password)
        )
        customerRepository.save(newCustomer)
    }

    fun findAll(): List<CustomerModel> {
        return customerRepository.findAll().toList()
    }

    fun delete(id: Int) {
        val customer = findById(id)
        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO

        customerRepository.save(customer)
    }

    fun update(customer: CustomerModel) {
        if(!customerRepository.existsById(customer.id!!)) {
            throw BadRequestException(Errors.ML1001.message.format(customer.id), Errors.ML1001.code)
        }

        customerRepository.save(customer)
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }

    fun findById(customerId: Int): CustomerModel = customerRepository.findById(customerId)
            .orElseThrow { NotFoundException(Errors.ML1001.message.format(customerId), Errors.ML1001.code) }

}
