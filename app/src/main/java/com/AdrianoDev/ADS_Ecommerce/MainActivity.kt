package com.AdrianoDev.ADS_Ecommerce
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.AdrianoDev.ADS_Ecommerce.Esquadrias
import com.AdrianoDev.ADS_Ecommerce.MateriaisLajes
import com.AdrianoDev.ADS_Ecommerce.Minerais
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.AdrianoDev.ADS_Ecommerce.model.listadeprodutos

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private lateinit var produtos: MutableList<listadeprodutos>
    private lateinit var navView: NavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var headerView: View
    private lateinit var nome: TextView
    private lateinit var imagem: ImageView
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onResume() {
        super.onResume()
        checkLoginState()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Verifica se o usuário está logado
        if (!isUserLoggedIn()) {
            // Se não estiver logado, inicia LoginActivity e finaliza a MainActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }


        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        checkLoginState()




        setSupportActionBar(binding.appBarMain.toolbar)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this , gso)
        val account:GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        navigationView = binding.navView
        headerView = navigationView.getHeaderView(0)
        nome = headerView.findViewById<TextView?>(R.id.nomeusuario)

        imagem = headerView.findViewById(R.id.fotodeperfil)


        navigationView.setItemIconTintList(null)
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_777)))
        navigationView.setBackground(ColorDrawable(getResources().getColor(R.color.azulfraco)))

        val button = headerView.findViewById<Button>(R.id.sair)
        val name = intent.getStringExtra("user")
        val fotouser = intent.getStringExtra("imagem")


        if (account != null) {
            Glide.with(applicationContext).load(account.photoUrl).into(imagem)
            nome.text = account.displayName

        } else {

        }


        binding.appBarMain.menssagem.setOnClickListener {
            Thread {
                val app = application as App
                val dao = app.db.listDao()
                val listaDeProdutos = dao.getALL()
                val gson = Gson()
                runOnUiThread {
                    val listaDeProdutosJson = gson.toJson(listaDeProdutos)
                    startActivity(Intent(this, CupomDeCompra::class.java).apply {
                        putExtra("listaDeProdutos", listaDeProdutosJson)
                        putExtra("type", "type")
                        putExtra("user", "$name")
                        putExtra("imagem","$fotouser")

                    })
                }
            }.start()


        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)



        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )




        button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Ao sair a sua lista de compras será apagada. Tem certeza que deseja sair?")
            builder.setPositiveButton("Sim") { _, _ ->
                GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .signOut()
                    .addOnCompleteListener(this) {
                        // Remove a chave isLoggedIn do SharedPreferences
                        val sharedPref = this.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            remove("isLoggedIn")
                            apply()
                        }
                        // Deleta todos os dados da tabela listadeprodutos
                        CoroutineScope(Dispatchers.IO).launch {
                            val app = application as App
                            app.db.listDao().deleteAll()
                        }
                        // Inicia LoginActivity e finaliza MainActivity
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
            }
            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.grupo_3)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val esquadrias = findViewById<ImageButton>(R.id.Esquadrias)

        Picasso.get()
            .load(R.drawable.esquadrias)
            .into(esquadrias)

        esquadrias.setOnClickListener {
            val Intent = Intent(this, Esquadrias::class.java)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")
            startActivity(Intent)
        }



        val pisos =findViewById<ImageButton>(R.id.Pisos)
        pisos.setOnClickListener {
            val Intent = Intent(this, Pisos::class.java)

            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")

            startActivity(Intent)
        }

        val lajes = findViewById<ImageButton>(R.id.Lajes)
        lajes.setOnClickListener {
            val Intent = Intent(this, MateriaisLajes::class.java)

            Intent.putExtra("nome",R.drawable.nomeescoras)
            Intent.putExtra("piso",R.drawable.imagemescoras)

            Intent.putExtra("nomeisopor",R.drawable.nomeisopor)
            Intent.putExtra("imagemisopor",R.drawable.imagemisopor)

            Intent.putExtra("nomemalha",R.drawable.nomemalha)
            Intent.putExtra("imagemmalha",R.drawable.imagemmalha)

            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")

            startActivity(Intent)
        }

        val minerios = findViewById<ImageButton>(R.id.Minerais)
        minerios.setOnClickListener {
            val Intent = Intent(this, Minerais::class.java,)

            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")


            startActivity(Intent)
        }

        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val isAppClosed = sharedPref.getBoolean("isAppClosed", false)

        if (!isAppClosed) {
            // iniciar a MainActivity
        } else {
            with (sharedPref.edit()) {
                putBoolean("isAppClosed", false)
                apply()
            }
        }

    }


    private fun isUserLoggedIn(): Boolean {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser != null
    }


    private fun checkLoginState() {
        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            // Redirecionar o usuário para a tela de login, se necessário
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        // não faz nada
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean("isAppClosed", true)
            apply()
        }

    }

}