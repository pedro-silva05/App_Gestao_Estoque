package com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrinho")
data class EntidadeCarrinho(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "descricao") val descricao: String,
    @ColumnInfo(name = "cod_barras") val cod_barras: String,
    @ColumnInfo(name = "preco") val preco: Double,
    @ColumnInfo(name = "quantidade") val quantidade: Int

)
