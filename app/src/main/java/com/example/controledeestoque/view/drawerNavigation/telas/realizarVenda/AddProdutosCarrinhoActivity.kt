package com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.controledeestoque.databinding.ActivityAddProdutosCarrinhoBinding
import com.example.controledeestoque.view.drawerNavigation.telas.estoque.Produto
import com.example.controledeestoque.view.utilidades.ProgressBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddProdutosCarrinhoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddProdutosCarrinhoBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListaProdutosAdapter
    private lateinit var arrayList: ArrayList<Produto>
    private lateinit var progressBar: ProgressBar
    private lateinit var carrinho: EntidadeCarrinho
    private lateinit var viewModel: CarrinhoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProdutosCarrinhoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            finish()
        }

        inicializarComponentes()
        chamarListaProdutos()
    }

    private fun inicializarComponentes(){
        arrayList = arrayListOf()
        adapter = ListaProdutosAdapter(arrayList)
        recyclerView = binding.recyclerViewList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = adapter

        progressBar = ProgressBar(this)
        viewModel = ViewModelProvider(this).get(CarrinhoViewModel::class.java)
    }

    private fun chamarListaProdutos(){
        progressBar.startLoading()
        databaseReference = FirebaseDatabase.getInstance().getReference("Produtos")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (produtoSnapshot in snapshot.children){
                    val mostrarProduto = produtoSnapshot.getValue(Produto::class.java)
                    mostrarProduto?.let {
                        arrayList.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
                adapter.setOnItemClickListener(object : ListaProdutosAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        val produto = arrayList[position]
                        carrinho = EntidadeCarrinho(
                            descricao = produto.descricao.toString(),
                            cod_barras = produto.cod_barras.toString(),
                            preco = produto.valor.toString().toDouble(),
                            quantidade = 1
                        )
                        viewModel.insirirAoCarrinho(carrinho)

                        Toast.makeText(this@AddProdutosCarrinhoActivity, "${produto.descricao} adicionado ao carrinho", Toast.LENGTH_SHORT).show()
                    }
                })
                progressBar.isDimiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AddProdutosCarrinhoActivity, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
                progressBar.isDimiss()
            }

        })
    }
}

