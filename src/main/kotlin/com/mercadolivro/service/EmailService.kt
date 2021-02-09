package com.mercadolivro.service

import com.mercadolivro.model.BookModel
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmailService(private val mailSender: MailSender) {

    fun sendBookSoldEmail(book: BookModel) {
        sendEmail(buildMailMessage(book))
    }

    /**
     * Vamo criar uma interface que tenha esse metodo, para nas config mostrar como usar um bean para prod e um para stg
     */
    private fun sendEmail(mailMessage: SimpleMailMessage) {
        mailSender.send(mailMessage)
    }

    /**
     * Esse metodo pode ficar em alguma classe intermediaria
     */
    private fun buildMailMessage(book: BookModel): SimpleMailMessage =
        SimpleMailMessage().apply {
            setFrom("nosso@email.no.properties")
            setSubject("Livro Vendido!")
            setSentDate(Date(System.currentTimeMillis()))
            setText("Seu livro ${book.name} foi vendido por R\$${book.price}")
        }
}