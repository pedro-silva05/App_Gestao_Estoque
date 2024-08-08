package com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class CarrinhoViewModel(application: Application): AndroidViewModel(application){

    private val carrinhoRepository: CarrinhoRepository
    val entidadeCarrinho: LiveData<List<EntidadeCarrinho>>
    init {
        val carrinhoDao = CarrinhoDatabase.getDatabase(application).carrinhoDao()
        carrinhoRepository = CarrinhoRepository(carrinhoDao)
        entidadeCarrinho = carrinhoRepository.getAllItens()
    }

    fun insirirAoCarrinho(entidadeCarrinho: EntidadeCarrinho){
        viewModelScope.launch {
            carrinhoRepository.insert(entidadeCarrinho)
        }
    }

    fun delete(entidadeCarrinho: EntidadeCarrinho){
        viewModelScope.launch {
            carrinhoRepository.deletar(entidadeCarrinho)
        }
    }

}