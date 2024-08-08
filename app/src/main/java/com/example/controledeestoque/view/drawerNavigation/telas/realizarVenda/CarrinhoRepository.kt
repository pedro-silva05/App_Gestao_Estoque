package com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda

import androidx.lifecycle.LiveData

class CarrinhoRepository(private val carrinhoDao: CarrinhoDao) {

    suspend fun insert(entidadeCarrinho: EntidadeCarrinho){
        carrinhoDao.inserirProduto(entidadeCarrinho)
    }

    suspend fun deletar(entidadeCarrinho: EntidadeCarrinho){
        carrinhoDao.deletarItem(entidadeCarrinho)
    }

    fun getAllItens(): LiveData<List<EntidadeCarrinho>>{
        return carrinhoDao.obterTodosProdutos()
    }
}