package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun findById(id: Int): BookModel =
            bookRepository.findById(id)
                    .orElseThrow { BadRequestException(Errors.ML2000.message.format(id), Errors.ML2000.code) }

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(pageable: Pageable): Page<BookModel> = bookRepository.findAll(pageable)

    fun delete(id: Int) {
        val book = findById(id)

        book.status = BookStatus.CANCELADO

        bookRepository.save(book)
    }

    fun update(book: BookModel) {
        bookRepository.save(book)
    }

    fun findActives(page: Int, size: Int, orderBy: String, direction: String): Page<BookModel> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy)
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
    }

    fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomerAndStatusIn(customer, listOf(BookStatus.ATIVO, BookStatus.CANCELADO))
        for(book in books) {
            book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
//        books.forEach {
//            it.status = BookStatus.DELETADO
//        }
    }

//    fun purchase(book: BookModel) =
//        book.apply {
//            soldAt = LocalDateTime.now()
//            status = BookStatus.VENDIDO
//        }
//            .let(bookRepository::save)
//            .also {
//                applicationEventPublisher.publishEvent(SoldBookEvent(this, it, it.customer!!))
//            }

    fun findAllByIds(bookIds: Set<Int>): List<BookModel> {
        return bookRepository.findAllById(bookIds).toList()
    }

    fun purchase(books: List<BookModel>) {
        books.map {
            it.status = BookStatus.VENDIDO
        }
        bookRepository.saveAll(books)
    }

}
