package com.mercadolivro.events.event

import com.mercadolivro.model.BookModel
import org.springframework.context.ApplicationEvent
import java.time.LocalDateTime

class SoldBookEvent(
    source: Any,
    val book: BookModel
): ApplicationEvent(source)