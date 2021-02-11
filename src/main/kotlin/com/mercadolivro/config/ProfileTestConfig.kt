package com.mercadolivro.config

import com.mercadolivro.service.email.EmailService
import com.mercadolivro.service.email.EmailServiceImpl
import com.mercadolivro.service.email.MockEmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.mail.MailSender

@Configuration
@Profile("test")
class ProfileTestConfig() {

    @Bean
    fun emailService(): EmailService = MockEmailService()

}