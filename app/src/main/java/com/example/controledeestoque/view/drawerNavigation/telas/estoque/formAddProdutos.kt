package com.example.controledeestoque.view.drawerNavigation.telas.estoque

import android.os.Bundle
import android.text.InputFilter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.ActivityFormAddProdutosBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class formAddProdutos : AppCompatActivity() {

    private lateinit var binding : ActivityFormAddProdutosBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFormAddProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            finish()
        }

        database = FirebaseDatabase.getInstance().reference

        binding.tituloHeader.text
        binding.descricao.setOnEditorActionListener { textView, actionId, event ->
            true
        }
        binding.codbarras.hint = getString(R.string.codbarras)
        binding.categoria.hint = getString(R.string.categoria)
        binding.valor.hint = getString(R.string.valor)
        binding.quantidade.hint = getString(R.string.quantidade)
        binding.addProduto.text = getString(R.string.add_produto)

        val maxCaracteres = 13
        binding.codbarras.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxCaracteres))

        binding.addProduto.setOnClickListener {

            val descricao = binding.descricao.text.toString()
            val cod_barras = binding.codbarras.text.toString()
            val categoria = binding.categoria.text.toString()
            val valor = binding.valor.text.toString().toDoubleOrNull()
            val quantidade = binding.quantidade.text.toString().toIntOrNull()

            if (binding.descricao.text.isEmpty() || binding.codbarras.text.isEmpty() || binding.categoria.text.isEmpty() || binding.quantidade.text.isEmpty()){
                Toast.makeText(this, "Por favor, Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else{
                when {

                    valor == null -> Toast.makeText(this, "Por favor, insira um valor válido", Toast.LENGTH_SHORT).show()
                    quantidade == null -> Toast.makeText(this, "Por favor, insira uma quantidade válida", Toast.LENGTH_SHORT).show()
                    else -> {
                        val produto = Produtos(
                            descricao,
                            cod_barras,
                            categoria,
                            valor,
                            quantidade
                        )
                        database.child("Produtos").push().setValue(produto)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Produto adicionado!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    this,
                                    "Erro ao adicionar produto: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
                finish()
            }
        }
    }
}

data class Produtos(
    val descricao: String,
    val cod_barras: String,
    val categoria: String,
    val valor: Double,
    val quantidade: Int
)