package com.example.controledeestoque.view.bottomNavigation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileIcon.setImageResource(R.drawable.baseline_menu_24)
        binding.searchInput.hint = getString(R.string.pesquisar)


        binding.searchInput.setOnClickListener {

        }

        produtoRecyclerView = view.findViewById(R.id.recyclerViewList)
        produtoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        produtoRecyclerView.setHasFixedSize(true)

        produtoArrayList = arrayListOf<Produto>()
        getProdutoData()

    }

    private fun getProdutoData(){
        dbref = FirebaseDatabase.getInstance().getReference("Produtos")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (produtosSnapshot in snapshot.children){
                        val produtos = produtosSnapshot.getValue(Produto::class.java)
                        produtoArrayList.add(produtos!!)
                    }

                    produtoRecyclerView.adapter = ProdutosAdapter(produtoArrayList)

                }
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