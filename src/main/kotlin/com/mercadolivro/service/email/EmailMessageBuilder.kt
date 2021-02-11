package com.mercadolivro.service.email

import com.mercadolivro.model.BookModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import java.util.*

abstract class EmailMessageBuilder: EmailService {

    @Value("\$spring.email.username")
    private lateinit var sender: String

    override fun sendBookSoldEmail(book: BookModel) {
        sendEmail(buildMailMessage(book))
    }

    protected fun buildMailMessage(book: BookModel): SimpleMailMessage =
        SimpleMailMessage().apply {
            setFrom(sender)
            setSubject("Livro Vendido!")
            setSentDate(Date(System.currentTimeMillis()))
            setText("Seu livro ${book.name} foi vendido por R\$${book.price}")
        }

}