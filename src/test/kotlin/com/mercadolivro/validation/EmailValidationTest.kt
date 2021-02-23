package com.mercadolivro.validation

import com.mercadolivro.service.CustomerService
import io.mockk.MockKAnnotations
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EmailValidationTest {

    @MockK
    private lateinit var customerService: CustomerService

    @InjectMockKs
    private lateinit var emailValidator: EmailAvailableValidator

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `returns false when email is empty`() {
        val result = emailValidator.isValid("", null)

        assertEquals(result, false)
    }

    @Test
    fun `returns false when email is null`() {
        val result = emailValidator.isValid(null, null)

        assertFalse(result)
    }

    @Test
    fun `returns false when email is already present`() {
        val email = "email@mercadolivro.com"
        every { customerService.emailAvailable(email) } returns false

        val result = emailValidator.isValid(email, null)

        assertFalse(result)
    }


    @Test
    fun `returns true when email is not present`() {
        val email = "email@mercadolivro.com"
        every { customerService.emailAvailable(email) } returns true

        val result = emailValidator.isValid(email, null)

        assertTrue(result)
    }
}