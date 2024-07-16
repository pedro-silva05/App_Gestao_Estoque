package com.example.controledeestoque.view.formLogin

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.ActivityFormLoginBinding
import com.example.controledeestoque.view.bottomNavigation.MainActivity
import com.example.controledeestoque.view.formCadastro.formCadastro

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()

    private fun navegarMainScreen(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigate = findViewById<TextView>(R.id.btn_navigate)
        navigate.setOnClickListener{
            val intent = Intent(this, formCadastro::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {

            val email = binding.emailLogin.text.toString()
            val senha = binding.senhaLogin.text.toString()

            if (email.isEmpty() || senha.isEmpty()){
                val toast = Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                toast.show()
            } else {
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { autenticacao ->
                    if (autenticacao.isSuccessful){
                        navegarMainScreen()
                    }
                }
            }
        }
    }
}