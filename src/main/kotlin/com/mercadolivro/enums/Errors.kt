package com.mercadolivro.enums

enum class Errors(val code: String, val message: String ) {

    ML1001("ML-1001", "Customer [%s] Not Exists"),
    ML1002("ML-1002", ""),

    ML2000("ML-2000", "Book [%s] Not Exists"),
    ML2001("ML-2001", "Você nao pode atualizar um registro cancelado ou deletado"),
    ML3000("ML-3000", "Requisição ta cagada")
}