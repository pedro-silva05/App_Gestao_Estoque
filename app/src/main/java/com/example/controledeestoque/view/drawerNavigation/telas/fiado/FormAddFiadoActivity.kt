package com.example.controledeestoque.view.drawerNavigation.telas.fiado

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.ActivityFormAddFiadoBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FormAddFiadoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFormAddFiadoBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFormAddFiadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        binding.backBtn.setOnClickListener{
            finish()
        }
        binding.addFiado.setOnClickListener {
            addFiado()
        }
    }

    private fun addFiado(){
        val nome = binding.nomeDevedor.text.toString()
        val limiteD = binding.limiteDivida.text.toString().toDoubleOrNull()
        val atualD = binding.atualDivida.text.toString().toDoubleOrNull()

        when{
            nome.isEmpty() -> Toast.makeText(this, "Preencha o nome do devedor!", Toast.LENGTH_SHORT).show()
            limiteD == null -> Toast.makeText(this, "Preencha o limite da dívida do devedor", Toast.LENGTH_SHORT).show()

            else -> {
                val fiados = Fiados(
                    nome,
                    limiteD,
                    atualD
                )
                database.child("Fiados").push().setValue(fiados)
                    .addOnSuccessListener { Toast.makeText(this, "Devedor adicionado!", Toast.LENGTH_SHORT).show() }
                    .addOnFailureListener { Toast.makeText(this, "Erro: ${it.message}", Toast.LENGTH_SHORT).show() }

                finish()
            }
        }
    }
}