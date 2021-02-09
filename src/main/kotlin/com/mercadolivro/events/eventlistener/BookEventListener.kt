package com.mercadolivro.events.eventlistener

import com.mercadolivro.events.event.SoldBookEvent
import com.mercadolivro.service.EmailService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class BookEventListener(private val emailService: EmailService){

    @EventListener
    fun handleBookSoldEvent(soldBookEvent: SoldBookEvent) {
        emailService.sendBookSoldEmail(soldBookEvent.book)
    }

}