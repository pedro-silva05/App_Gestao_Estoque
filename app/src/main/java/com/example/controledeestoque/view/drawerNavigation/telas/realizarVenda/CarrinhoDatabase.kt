package com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntidadeCarrinho::class], version = 1, exportSchema = false)
abstract class CarrinhoDatabase : RoomDatabase() {

    abstract fun carrinhoDao(): CarrinhoDao

    companion object {
        @Volatile
        private var INSTANCE: CarrinhoDatabase? = null

        fun getDatabase(context: Context): CarrinhoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarrinhoDatabase::class.java, "carrinho_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}
