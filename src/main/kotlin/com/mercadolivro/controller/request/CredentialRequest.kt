package com.mercadolivro.controller.request

data class CredentialRequest(
    val email: String?,
    val password: String?
) {
    constructor() : this(null, null) {

    }
}
