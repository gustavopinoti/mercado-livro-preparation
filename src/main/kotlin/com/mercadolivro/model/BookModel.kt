package com.mercadolivro.model

import org.springframework.transaction.support.TransactionSynchronizationManager
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "book")
data class BookModel(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    var customer: CustomerModel? = null,

    @Column(name = "customer_id")
    var customerId: Int? = null,

    @Column(name = "is_active")
    var isActive: Boolean = true
)