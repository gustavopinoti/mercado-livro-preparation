package com.mercadolivro.events.event

import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.PurchaseModel
import org.springframework.context.ApplicationEvent
import java.time.LocalDateTime

class PurchaseEvent(
    source: Any,
    val purchaseModel: PurchaseModel
): ApplicationEvent(source)