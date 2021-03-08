package com.mercadolivro.service

import com.mercadolivro.controller.request.BookPutRequest
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.events.event.SoldBookEvent
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.extensions.toBookModel
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun findById(id: Int): BookModel =
            bookRepository.findById(id)
                    .orElseThrow { BadRequestException("Book [${id}] Not Exists") }

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(): List<BookModel> = bookRepository.findAll().toList()

    fun delete(id: Int) {
        val book = findById(id)

        book.status = BookStatus.CANCELADO

        bookRepository.save(book)
    }

    fun update(id: Int, bookPutRequest: BookPutRequest) {
        val bookSaved = findById(id)

        val book = bookPutRequest.toBookModel(bookSaved)

        bookRepository.save(book)
    }

    fun findActives(): List<BookModel> =
        bookRepository.findByStatus(BookStatus.ATIVO)

    fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomer(customer)
        for(book in books) {
            book.deletedAt = LocalDateTime.now()
            book.status = BookStatus.DELETADO
        }
//        books.forEach {
//            it.deletedAt = LocalDateTime.now()
//            it.status = BookStatus.DELETADO
//        }
    }

    fun purchase(book: BookModel) {
        book.saleDate = LocalDateTime.now()
        book.status = BookStatus.VENDIDO

        bookRepository.save(book)

        applicationEventPublisher.publishEvent(SoldBookEvent(this, book, book.customer!!))
    }

}
