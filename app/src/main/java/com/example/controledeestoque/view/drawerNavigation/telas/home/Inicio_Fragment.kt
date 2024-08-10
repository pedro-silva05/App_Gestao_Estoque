package com.example.controledeestoque.view.drawerNavigation.telas.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.controledeestoque.databinding.FragmentInicioBinding
import com.example.controledeestoque.view.utilidades.MaterialShape

class Inicio_Fragment : Fragment() {

    private lateinit var binding: FragmentInicioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInicioBinding.inflate(layoutInflater)

        MaterialShape.applyCutCorners(requireContext(), binding.btnCaixa)
        MaterialShape.applyCutCorners(requireContext(), binding.btnFiado)
        MaterialShape.applyCutCorners(requireContext(), binding.btnEstoque)

        return binding.root
    }
}