package com.example.controledeestoque.view.bottomNavigation.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.FragmentDashboardBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DashboardFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().reference

        binding.tituloHeader.text
        binding.descricao.setOnEditorActionListener { textView, actionId, event ->
            true
        }
        binding.codbarras.hint = getString(R.string.codbarras)
        binding.categoria.hint = getString(R.string.categoria)
        binding.valor.hint = getString(R.string.valor)
        binding.quantidade.hint = getString(R.string.quantidade)
        binding.dataVencimento.hint = getString(R.string.data_vencimento)
        binding.addProduto.text = getString(R.string.add_produto)
        binding.addProduto.setOnClickListener {

            val descricao = binding.descricao.text.toString()
            val cod_barras = binding.codbarras.text.toString()
            val categoria = binding.categoria.text.toString()
            val valor = binding.valor.text.toString().toDoubleOrNull()
            val quantidade = binding.quantidade.text.toString().toIntOrNull()
            val data_vencimento = binding.dataVencimento.toString()

            when {
                valor == null -> Toast.makeText(requireContext(), "Por favor, insira um valor válido", Toast.LENGTH_SHORT).show()
                quantidade == null -> Toast.makeText(requireContext(), "Por favor, insira uma quantidade válida", Toast.LENGTH_SHORT).show()
                else -> {
                    val produto = Produto(
                        descricao,
                        cod_barras,
                        categoria,
                        valor,
                        quantidade,
                        data_vencimento
                    )
                    database.child("Produtos").push().setValue(produto)
                        .addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                "Produto adicionado!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                requireContext(),
                                "Erro ao adicionar produto: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Produto(
    val descricao:String,
    val cod_barras: String,
    val categoria:String,
    val valor:Double,
    val quantidade:Int,
    val data_vencimento:String
)