package com.example.controledeestoque.view.bottomNavigation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbref: DatabaseReference
    private lateinit var produtoRecyclerView: RecyclerView
    private lateinit var produtoArrayList: ArrayList<Produto>
    private lateinit var adapter: ProdutosAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
        dbref = FirebaseDatabase.getInstance().getReference("Produtos")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                produtoArrayList.clear()
                    for (produtosSnapshot in snapshot.children){
                        val produtos = produtosSnapshot.getValue(Produto::class.java)
                        produtos?.let {
                            produtoArrayList.add(it)
                        }
                    }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}