package com.mercadolivro.service.email

import org.slf4j.LoggerFactory
import org.springframework.mail.SimpleMailMessage

class MockEmailService: EmailMessageBuilder() {

    val logger = LoggerFactory.getLogger(this.javaClass)

    override fun sendEmail(mailMessage: SimpleMailMessage) {
        logger.info(mailMessage.toString())
    }

}