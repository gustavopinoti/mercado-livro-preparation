package com.mercadolivro.model

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.exception.BadRequestException
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "book")
data class BookModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null,

    @Column(name = "sold_at")
    var soldAt: LocalDateTime? = null
) {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if(field == BookStatus.DELETADO || field == BookStatus.CANCELADO)
                throw BadRequestException("Você nao pode atualizar um registro cancelado ou deletado")
            field = value
        }

    constructor(id: Int? = null,
                name: String,
                price: BigDecimal,
                customer: CustomerModel? = null,
                saleDate: LocalDateTime? = null,
                status: BookStatus?): this(id, name, price, customer, saleDate) {
        this.status = status
    }

    init {
        status = status ?: BookStatus.ATIVO
    }

}

