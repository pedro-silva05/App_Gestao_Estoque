package com.example.controledeestoque.view.drawerNavigation.telas.fiado

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.ActivityDetalhesFiadoBinding

class DetalhesFiadoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetalhesFiadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalhesFiadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            finish()
        }

        val bundle : Bundle? = intent.extras
        val nome = bundle!!.getString("nome")
        val limiteD = bundle.getString("limiteD")
        val atualD = bundle.getString("atualD")

        binding.detalhesNomeDevedor.text = nome
        binding.detalhesLimiteDivida.text = limiteD.toString()
        binding.detalhesAtualDivida.text = atualD.toString()
    }
}