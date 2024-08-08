package com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controledeestoque.databinding.FragmentRealizarVendaBinding

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
}
