package com.example.controledeestoque.view.formCadastro

import android.annotation.SuppressLint
import android.content.Intent
import  android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.ActivityFormCadastroBinding
import com.example.controledeestoque.view.formLogin.FormLogin
import com.example.controledeestoque.view.utilidades.MaterialShape
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase
class formCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        MaterialShape.applyStyleTextInput(this, binding.email)
        MaterialShape.applyStyleTextInput(this, binding.senha)
        MaterialShape.applyStyleTextInput(this, binding.confSenha)
        MaterialShape.applyCutCorners(this, binding.btnCadastrar)



        val navigate = findViewById<TextView>(R.id.btn_navigate)
        navigate.setOnClickListener{
            irParaLogin()
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
                        val user = auth.currentUser
                        user?.let {
                            val uid = it.uid
                            addUsuarioDatabase(uid)
                        }
                        val toast = Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                        toast.show()
                        irParaLogin()
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

    private fun addUsuarioDatabase(uid: String){
        val database  = FirebaseDatabase.getInstance()
        val userRef = database.getReference("Users/$uid")

        val dadosUsuario = mapOf(
            "Email: " to binding.email.text.toString()
        )

        userRef.setValue(dadosUsuario)
            .addOnCompleteListener { dados ->
                if (dados.isSuccessful){
                    Log.d("Sucesso", "Dados enviados!")
                }
            }.addOnFailureListener { e ->
                Log.e("Erro", "${e.message}")
            }
    }

    private fun irParaLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
    }
}