package com.example.controledeestoque.view.drawerNavigation.telas.fiado

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controledeestoque.databinding.FragmentFiadoBinding
import com.example.controledeestoque.view.utilidades.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Fiado_Fragment : Fragment() {


    private lateinit var binding: FragmentFiadoBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterFiado: AdapterFiado
    private lateinit var arrayList: ArrayList<Fiados>
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiadoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = ProgressBar(requireActivity())
        inicializarComponentes()

        binding.floatingBtn.setOnClickListener {
            val intent = Intent(activity, FormAddFiadoActivity::class.java)
            startActivity(intent)
        }
        criarListaFiado()
    }

    private fun inicializarComponentes(){
        arrayList = arrayListOf()
        recyclerView = binding.recyclerViewList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        adapterFiado = AdapterFiado(arrayList)
        recyclerView.adapter = adapterFiado
    }

    private fun criarListaFiado(){
        progressBar.startLoading()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users/$uid/Fiados")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (fiadosSnapshot in snapshot.children){
                    val mostrarFiados = fiadosSnapshot.getValue(Fiados::class.java)
                    mostrarFiados?.let {
                        arrayList.add(it)
                    }
                }
                adapterFiado.notifyDataSetChanged()
                adapterFiado.setOnItemClickListener(object : AdapterFiado.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        val intent = Intent(activity, DetalhesFiadoActivity::class.java)
                        intent.putExtra("nome", arrayList[position].nome)
                        intent.putExtra("limiteD", arrayList[position].limiteD.toString())
                        intent.putExtra("atualD", arrayList[position].atualD.toString())
                        startActivity(intent)
                    }
                })
                progressBar.isDimiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Erro ao carregar os dados", Toast.LENGTH_SHORT).show()
                progressBar.isDimiss()
            }

        })
    }
}