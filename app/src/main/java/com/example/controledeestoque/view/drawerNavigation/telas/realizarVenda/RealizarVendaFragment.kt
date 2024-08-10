package com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controledeestoque.databinding.FragmentRealizarVendaBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class RealizarVendaFragment : Fragment() {

    private lateinit var binding: FragmentRealizarVendaBinding
    private lateinit var adapter: CarrinhoAdapter
    private val viewModel: CarrinhoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRealizarVendaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        styleComponents()

        binding.floatingBtn.setOnClickListener {
            val intent = Intent(activity, AddProdutosCarrinhoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = CarrinhoAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.entidadeCarrinho.observe(viewLifecycleOwner, Observer { itens ->
            itens?.let {
                adapter.setCarrinhoList(it)
            }
        })
    }

    private fun styleComponents(){
        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopRightCorner(CornerFamily.CUT, 30f)
            .setBottomLeftCorner(CornerFamily.CUT, 30f)
            .build()

        val btnVender = MaterialShapeDrawable(shapeAppearanceModel).apply {
            fillColor = ColorStateList.valueOf(Color.GREEN)
        }

        val btnFiado = MaterialShapeDrawable(shapeAppearanceModel).apply {
            fillColor = ColorStateList.valueOf(Color.RED)
        }

        binding.btnPagamento.setTextColor(Color.BLACK)
        binding.btnAddAoFiado.setTextColor(Color.WHITE)
        binding.btnPagamento.background = btnVender
        binding.btnAddAoFiado.background = btnFiado
    }
}
