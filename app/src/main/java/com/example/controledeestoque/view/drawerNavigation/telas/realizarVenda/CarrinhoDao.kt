package com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CarrinhoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserirProduto(entidadeCarrinho: EntidadeCarrinho)

    @Query("SELECT * FROM carrinho")
    fun obterTodosProdutos(): LiveData<List<EntidadeCarrinho>>

    @Delete
    suspend fun deletarItem(entidadeCarrinho: EntidadeCarrinho)

}