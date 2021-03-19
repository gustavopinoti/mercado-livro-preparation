package com.mercadolivro.controller

import com.mercadolivro.controller.request.BookPostRequest
import com.mercadolivro.controller.request.BookPutRequest
import com.mercadolivro.controller.request.PurchasePostRequest
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.extensions.toBookModel
import com.mercadolivro.extensions.toBookResponse
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
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
    fun findAll(@PageableDefault(page = 0, size = 12) pageable: Pageable): Page<BookResponse> {
        return bookService.findAll(pageable).map { it.toBookResponse() }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): BookResponse {
        return bookService.findById(id).toBookResponse()
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    fun findActives(@RequestParam(value="page", defaultValue="0") page: Int,
                    @RequestParam(value="linesPerPage", defaultValue="24") size: Int,
                    @RequestParam(value="orderBy", defaultValue="id") orderBy: String,
                    @RequestParam(value="direction", defaultValue="DESC") direction: String) =
        bookService.findActives(page, size, orderBy, direction)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Int) {
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable("id") id: Int, @Valid @RequestBody book: BookPutRequest) {
        val bookSaved = bookService.findById(id)
        val bookModel = book.toBookModel(bookSaved)
        bookService.update(bookModel)
    }

    @PostMapping("purchase")
    @ResponseStatus(HttpStatus.OK)
    fun purchase(@Valid @RequestBody purchase: PurchasePostRequest) {
        //TODO pensei em colocar depois um obj muitos para muitos aqui. Por isso deixei recebendo o id do usuario
        val book = bookService.findById(purchase.bookId)
        bookService.purchase(book)
    }

}
