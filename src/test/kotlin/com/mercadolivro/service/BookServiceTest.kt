package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Profiles
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationEventPublisher
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
class BookServiceTest {

    //TODO Testes falhando
    @MockK
    private lateinit var bookRepository: BookRepository

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMockKs
    private lateinit var bookService: BookService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Nested
    inner class WhenCallingFindById {
        @Test
        fun `throws a error when has no book for id`() {
            val bookId = 123
            every { bookRepository.findById(bookId) } returns Optional.empty()

            assertThrows(BadRequestException::class.java) {
                bookService.findById(bookId)
            }
        }

        @Test
        fun `returns a book when has book for id`() {
            val bookId = 123
            every { bookRepository.findById(bookId) } returns Optional.of(buildBook(bookId))

            val result = bookService.findById(bookId)

            assertEquals(result, buildBook(bookId))
        }
    }

    @Nested
    inner class WhenCallingFindAll {
        @Test
        fun `returns all the books`() {
            val books = listOf(buildBook(1), buildBook(2))
            every { bookRepository.findAll() } returns books

            val result = bookService.findAll()

            assertEquals(result, books)
        }
    }

    @Nested
    inner class WhenCallingCreate {
        @Test
        fun `returns created book`() {
            val book = buildBook(null)
            val createdBook = buildBook(1)
            every { bookRepository.save(book) } returns createdBook

            bookService.create(book)

            verify(exactly = 1) { bookRepository.save(book) }
        }
    }

    @Nested
    inner class WhenCallingUpdate {
        @Test
        fun `returns updated book`() {
            val bookId = 123
            val book = buildBook(bookId)
            val updatedBook = buildBook(bookId, name = "new name")
            every { bookRepository.save(book) } returns updatedBook
            every { bookRepository.findById(bookId) } returns Optional.of(updatedBook)

            bookService.update(book)

            verify(exactly = 1) { bookRepository.save(book) }
        }
    }

    @Nested
    inner class WhenCallingActive {
        @Test
        fun `returns all the active books`() {
            val books = listOf(buildBook(1), buildBook(2))
            every { bookRepository.findAll() } returns books

            val result = bookService.findAll()

            assertEquals(result, books)
        }
    }

    @Nested
    inner class WhenCallingPurchase() {
        @Test
        fun `changes the book status and send a event`() {
            val book = buildBook(123)
            every { applicationEventPublisher.publishEvent(any()) } just runs
            every { bookRepository.save(book) } returns book

            bookService.purchase(book)

            assertAll(
                    { assertNotNull(book.saleDate) },
                    { assertNotNull(book.status) },
                    { verify(exactly = 1) { applicationEventPublisher.publishEvent(any()) } }
            )
        }
    }

    private fun buildBook(bookId: Int?, name: String = "Example book", bookStatus: BookStatus = BookStatus.ATIVO) =
        BookModel(
            bookId,
                name,
                BigDecimal.TEN,
                buildCustomer(),
                bookStatus,
                null
        )

    private fun buildCustomer() =
        CustomerModel(
                123,
                "Some Custoemr",
                "some@customer.com",
                mutableSetOf(Profiles.CUSTOMER)
        )
}