package com.example.controledeestoque.view.bottomNavigation.ui.dashboard

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.FragmentDashboardBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        val maxCaracteres = 13
        binding.codbarras.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxCaracteres))

        val dateToFormat = Date()
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding.dataVencimento.setText(sdf.format(dateToFormat))

        binding.addProduto.setOnClickListener {

            val descricao = binding.descricao.text.toString()
            val cod_barras = binding.codbarras.text.toString()
            val categoria = binding.categoria.text.toString()
            val valor = binding.valor.text.toString().toDoubleOrNull()
            val quantidade = binding.quantidade.text.toString().toIntOrNull()
            val data_vencimento = binding.dataVencimento.toString()

            if (binding.descricao.text.isEmpty() || binding.codbarras.text.isEmpty() || binding.categoria.text.isEmpty() || binding.quantidade.text.isEmpty() || binding.dataVencimento.text.isEmpty()){
                Toast.makeText(requireContext(), "Por favor, Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else{
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