package com.mercadolivro.controller

import com.mercadolivro.controller.request.BookPostRequest
import com.mercadolivro.controller.request.BookPutRequest
import com.mercadolivro.extensions.toBookModel
import com.mercadolivro.model.BookModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("book")
class BookController(val bookService: BookService, val customerService: CustomerService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody book: BookPostRequest) {
        val customer = customerService.findById(book.customerId)
        bookService.create(book.toBookModel(customer))
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<BookModel> {
        return bookService.findAll()
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    fun findActives(): List<BookModel> {
        return bookService.findActives()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Int) {
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable("id") id: Int, @Valid @RequestBody book: BookPutRequest) {
        bookService.update(book.toBookModel(id))
    }

}
