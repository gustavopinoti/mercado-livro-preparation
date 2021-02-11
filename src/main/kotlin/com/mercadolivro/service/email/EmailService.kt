package com.mercadolivro.service.email

import com.mercadolivro.model.BookModel
import org.springframework.mail.SimpleMailMessage

interface EmailService {

    fun sendEmail(mailMessage: SimpleMailMessage)

    fun sendBookSoldEmail(book: BookModel)

}