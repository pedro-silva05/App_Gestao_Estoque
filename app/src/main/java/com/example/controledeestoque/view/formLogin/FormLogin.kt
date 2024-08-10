package com.example.controledeestoque.view.formLogin

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.ActivityFormLoginBinding
import com.example.controledeestoque.view.drawerNavigation.DrawerActivity
import com.example.controledeestoque.view.formCadastro.formCadastro
import com.example.controledeestoque.view.utilidades.MaterialShape
import com.example.controledeestoque.view.utilidades.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()
    private lateinit var progressBar: ProgressBar

    private fun navegarMainScreen(){
        val intent = Intent(this, DrawerActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MaterialShape.applyCutCorners(this, binding.btnLogin)
        MaterialShape.applyStyleTextInput(this, binding.emailLogin)
        MaterialShape.applyStyleTextInput(this, binding.senhaLogin)

        val progressBar = ProgressBar(this)

        val navigate = findViewById<TextView>(R.id.btn_navigate)
        navigate.setOnClickListener{
            val intent = Intent(this, formCadastro::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            progressBar.startLoading()
            val email = binding.emailLogin.text.toString()
            val senha = binding.senhaLogin.text.toString()

            if (email.isEmpty() || senha.isEmpty()){
                progressBar.isDimiss()
                val toast = Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                toast.show()
            } else {
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { autenticacao ->
                    if (autenticacao.isSuccessful) {
                        progressBar.isDimiss()
                        navegarMainScreen()
                    }
                }.addOnFailureListener { exception ->
                    val msgError = when(exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Email ou Senha incorretos!"
                        is FirebaseAuthInvalidUserException -> "Usuário não encontrado"
                        else -> {exception.message}
                    }
                    progressBar.isDimiss()
                    Toast.makeText(this,msgError, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}