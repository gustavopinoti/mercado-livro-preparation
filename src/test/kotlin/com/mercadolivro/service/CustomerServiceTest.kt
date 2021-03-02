package com.mercadolivro.service

import com.mercadolivro.enums.Profiles
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import com.mercadolivro.repository.CustomerRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationEventPublisher
import java.util.*

@SpringBootTest
class CustomerServiceTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @InjectMockKs
    private lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `calls save method one time when create a customer`() {
        val customer = buildCustomer(1)
        every { customerRepository.save(customer) } returns customer

        customerService.create(customer)

        verify(exactly = 1) { customerRepository.save(customer) }
    }

    @Test
    fun `returns all book when findAll method is called`() {
        val books = listOf(buildCustomer(1), buildCustomer(2))
        every { customerRepository.findAll() } returns books

        val result = customerService.findAll()

        Assertions.assertEquals(result, books)
    }

    @Test
    fun `calls delete method one time when delete is called`() {
        val id = 123
        every { customerRepository.deleteById(id) } just runs
        every { customerRepository.existsById(id) } returns true

        customerService.delete(id)

        verify(exactly = 1) { customerRepository.deleteById(id) }
    }

    @Test
    fun `throws a exception when delete is called`() {
        val id = 123
        every { customerRepository.deleteById(id) } just runs
        every { customerRepository.existsById(id) } returns false

        assertThrows<BadRequestException> { customerService.delete(id) }
    }

    @Test
    fun `throws a exception when update is called`() {
        val id = 123
        val customer = buildCustomer(id)
        every { customerRepository.existsById(id) } returns false

        assertThrows<BadRequestException> { customerService.update(customer) }
    }

    @Test
    fun `calls save method one time when update is called`() {
        val id = 123
        val customer = buildCustomer(id)
        every { customerRepository.save(customer) } returns customer
        every { customerRepository.existsById(id) } returns true

        customerService.update(customer)

        verify(exactly = 1) { customerRepository.save(customer) }
    }

    @Test
    fun `returns true when email is available`() {
        val email = "somer@email.com"
        every { customerRepository.existsByEmail(email) } returns false

        val result = customerService.emailAvailable(email)

        assertTrue(result)
    }

    @Test
    fun `returns false when email is not available`() {
        val email = "somer@email.com"
        every { customerRepository.existsByEmail(email) } returns true

        val result = customerService.emailAvailable(email)

        assertFalse(result)
    }

    @Test
    fun `returns not found when calling findById`() {
        val id = 123
        every { customerRepository.findById(id) } returns Optional.empty()

        assertThrows<NotFoundException> { customerService.findById(id) }
    }

    @Test
    fun `returns a customer when calling findById`() {
        val id = 123
        val customer = buildCustomer(id)
        every { customerRepository.findById(id) } returns Optional.of(customer)

        val result = customerService.findById(id)

        assertEquals(customer, result)
    }

    private fun buildCustomer(id: Int? = null) =
            CustomerModel(
                    id,
                    "Some Custoemr",
                    "some@customer.com",
                    mutableSetOf(Profiles.CUSTOMER)
            )
}