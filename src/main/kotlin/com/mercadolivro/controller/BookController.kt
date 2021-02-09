package com.mercadolivro.controller

import com.mercadolivro.controller.request.BookPostRequest
import com.mercadolivro.controller.request.CustomerPutRequest
import com.mercadolivro.extensions.toBookModel
import com.mercadolivro.extensions.toCustomerModel
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("book")
class BookController(val bookService: BookService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody book: BookPostRequest) {
        bookService.create(book.toBookModel())
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<BookModel> {
        return bookService.findAll()
    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    fun delete(@PathVariable("id") id: Int) {
//        bookService.delete(id)
//    }
//
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    fun update(@PathVariable("id") id: Int, @Valid @RequestBody customer: CustomerPutRequest) {
//        bookService.update(customer.toCustomerModel(id))
//    }

}
