package com.example.controledeestoque.view.formCadastro

import android.annotation.SuppressLint
import android.content.Intent
import  android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Snackbar
import androidx.compose.ui.graphics.Color
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.ActivityFormCadastroBinding
import com.example.controledeestoque.view.formLogin.FormLogin
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.ktx.Firebase

class formCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val navigate = findViewById<TextView>(R.id.btn_navigate)
        navigate.setOnClickListener{
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
        }

        binding.btnCadastrar.setOnClickListener{

            val email = binding.email.text.toString()
            val senha = binding.senha.text.toString()
            val confSenha = binding.confSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()){
                val toast = Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                toast.show()
            } else if (senha != confSenha){
                val toast = Toast.makeText(this, "As senhas são diferentes!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                toast.show()
            } else {
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener{ cadastro ->
                    if(cadastro.isSuccessful){
                        val toast = Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                        toast.show()
                        binding.email.setText("")
                        binding.senha.setText("")

                    }
                }.addOnFailureListener{ exception ->
                    val msgError = when(exception){
                        is FirebaseAuthWeakPasswordException -> "A senha deve ter no mínimo 6 caractéres!"
                        is FirebaseAuthInvalidCredentialsException -> "Informe um email válido!"
                        is FirebaseAuthUserCollisionException -> "Este email já está cadastrado!"
                        is FirebaseNetworkException -> "Erro de conexão. Verifique sua internet!"
                        else -> "Erro ao cadastrar usuário"
                    }
                    val toast = Toast.makeText(this, msgError, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                    toast.show()

                }
            }
        }
    }
}