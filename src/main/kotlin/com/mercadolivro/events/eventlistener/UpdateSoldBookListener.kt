package com.mercadolivro.events.eventlistener

import com.mercadolivro.events.event.PurchaseEvent
import com.mercadolivro.service.BookService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UpdateSoldBookListener(
    private val bookService: BookService,
){
    @Async
    @EventListener
    fun changeBookStatus(purchaseEvent: PurchaseEvent) {
        val books = purchaseEvent.purchaseModel.books
        bookService.purchase(books)
    }
}
