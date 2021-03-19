package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Profiles
import javax.persistence.*

@Entity(name = "customer")
data class CustomerModel (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    var status: CustomerStatus,

    @ElementCollection(targetClass = Profiles::class)
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Profiles> = mutableSetOf()
)
