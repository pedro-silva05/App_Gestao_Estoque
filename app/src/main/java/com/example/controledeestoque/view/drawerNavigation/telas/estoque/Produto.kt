package com.example.controledeestoque.view.drawerNavigation.telas.estoque

data class Produto(
    var descricao: String?= null,
    var cod_barras: String? = null,
    var categoria: String? = null,
    var valor: Double? = null,
    var quantidade: Int? = null
)
