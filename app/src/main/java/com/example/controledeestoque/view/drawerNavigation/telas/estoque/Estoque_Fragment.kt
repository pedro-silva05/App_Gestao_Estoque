package com.example.controledeestoque.view.drawerNavigation.telas.estoque

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.ActivityFormAddProdutosBinding
import com.example.controledeestoque.databinding.FragmentEstoqueBinding
import com.example.controledeestoque.view.utilidades.ProgressBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Estoque_Fragment : Fragment() {

    private var _binding: FragmentEstoqueBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbref: DatabaseReference
    private lateinit var produtoRecyclerView: RecyclerView
    private lateinit var produtoArrayList: ArrayList<Produto>
    private lateinit var adapter: ProdutosAdapter
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEstoqueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = ProgressBar(requireActivity())

        binding.floatingBtn.setOnClickListener {
            val intent = Intent(activity, formAddProdutos::class.java)
            startActivity(intent)
        }

        produtoRecyclerView = view.findViewById(R.id.recyclerViewList)
        produtoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        produtoRecyclerView.setHasFixedSize(true)

        produtoArrayList = arrayListOf()
        adapter = ProdutosAdapter(produtoArrayList)
        produtoRecyclerView.adapter = adapter
        getProdutoData()
        searchProdutos()

    }

    private fun searchProdutos(){
        binding.searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }


            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }

        })
    }

    private fun searchList(text: String) {
        val searchList = ArrayList<Produto>()
        for (data in produtoArrayList) {
            data.descricao?.let {
                if (it.lowercase().contains(text.lowercase())) {
                    searchList.add(data)
                }
            }
        }
        adapter.searchData(searchList)
    }

    private fun getProdutoData(){
        progressBar.startLoading()
        dbref = FirebaseDatabase.getInstance().getReference("Produtos")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                produtoArrayList.clear()
                for (produtosSnapshot in snapshot.children){
                    val produtos = produtosSnapshot.getValue(Produto::class.java)
                    produtos?.let {
                        produtoArrayList.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
                progressBar.isDimiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Erro ao carregar os dados", Toast.LENGTH_SHORT).show()
                progressBar.isDimiss()
            }
        })
    }
}