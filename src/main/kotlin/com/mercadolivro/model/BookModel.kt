package com.mercadolivro.model

import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.persistence.*

@Entity(name = "book")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int?,

    @Column
    var name: String,

    @Column
    var price: Double,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel
)