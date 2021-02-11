package com.mercadolivro.events.event

import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import org.springframework.context.ApplicationEvent
import java.time.LocalDateTime

class SoldBookEvent(
    source: Any,
    val book: BookModel,
    val customerModel: CustomerModel
): ApplicationEvent(source)