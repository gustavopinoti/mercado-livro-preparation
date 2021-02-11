package com.mercadolivro.service.email

import com.mercadolivro.model.BookModel
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.util.*

class EmailServiceImpl(private val mailSender: MailSender): EmailMessageBuilder() {

    override fun sendEmail(mailMessage: SimpleMailMessage) {
        mailSender.send(mailMessage)
    }

}