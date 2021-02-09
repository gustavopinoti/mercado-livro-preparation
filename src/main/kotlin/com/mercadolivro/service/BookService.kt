package com.mercadolivro.service

import com.mercadolivro.events.event.SoldBookEvent
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.model.BookModel
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

        book.isActive = false

        bookRepository.save(book)
    }

    fun update(book: BookModel) {
        val bookSaved = findById(book.id!!)

        book.customer = bookSaved.customer

        bookRepository.save(book)
    }

    fun findActives(): List<BookModel> =
        bookRepository.findByIsActive(true)

    fun purchase(book: BookModel) {
        book.saleDate = LocalDateTime.now()

        bookRepository.save(book)

        applicationEventPublisher.publishEvent(SoldBookEvent(this, book))
    }

}
