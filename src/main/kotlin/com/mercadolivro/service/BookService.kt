package com.mercadolivro.service

import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.model.BookModel
import com.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(): List<BookModel> {
        return bookRepository.findAll().toList()
    }


    fun delete(id: Int) {
        if(!bookRepository.existsById(id)) {
            throw BadRequestException("Book [${id}] Not Exists")
        }

        bookRepository.deleteById(id)
    }

    fun update(book: BookModel) {
        if (!bookRepository.existsById(book.id!!)) {
            throw BadRequestException("Book [${book.id}] Not Exists")
        }

        bookRepository.save(book)
    }



}
