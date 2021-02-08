package com.mercadolivro.service

import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(val customerRepository: CustomerRepository) {


    fun create(customer: CustomerModel) {
        customerRepository.save(customer)
    }

    fun findAll(): List<CustomerModel> {
        return customerRepository.findAll().toList()
    }

    fun delete(id: Int) {
        if(!customerRepository.existsById(id)) {
            throw BadRequestException("Customer [${id}] Not Exists")
        }

        customerRepository.deleteById(id)
    }

    fun update(customer: CustomerModel) {
        if(!customerRepository.existsById(customer.id!!)) {
            throw BadRequestException("Customer [${customer.id}] Not Exists")
        }

        customerRepository.save(customer)
    }

}
