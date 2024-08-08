package com.example.controledeestoque.view.drawerNavigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.controledeestoque.R
import com.example.controledeestoque.databinding.ActivityDrawerBinding
import com.example.controledeestoque.view.drawerNavigation.telas.estoque.Estoque_Fragment
import com.example.controledeestoque.view.drawerNavigation.telas.Inicio_Fragment
import com.example.controledeestoque.view.drawerNavigation.telas.fiado.Fiado_Fragment
import com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda.RealizarVendaFragment
import com.example.controledeestoque.view.formLogin.FormLogin
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityDrawerBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityDrawerBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toogle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        if (savedInstanceState == null){
            replaceFragment(Inicio_Fragment(), getString(R.string.inicio))
            navigationView.setCheckedItem(R.id.nav_inicio)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_inicio -> replaceFragment(Inicio_Fragment(), getString(R.string.inicio))
            R.id.nav_estoque -> replaceFragment(Estoque_Fragment(), getString(R.string.estoque))
            R.id.nav_fiado -> replaceFragment(Fiado_Fragment(), getString(R.string.fiado))
            R.id.nav_venda -> replaceFragment(RealizarVendaFragment(), getString(R.string.venda))
            R.id.log_out -> sairConta()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment : Fragment, title : String){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
        supportActionBar?.title = title
    }

    private fun sairConta(){
        auth = FirebaseAuth.getInstance()
        auth.signOut()

        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)

        finish()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}