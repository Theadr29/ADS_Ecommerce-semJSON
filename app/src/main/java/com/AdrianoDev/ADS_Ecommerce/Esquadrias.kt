package com.AdrianoDev.ADS_Ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityEsquadriasBinding
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.*


class Esquadrias : AppCompatActivity() {

    private lateinit var binding: ActivityEsquadriasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esquadrias)

        binding = ActivityEsquadriasBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dados = intent.extras


        var name = dados?.getString("user")

        var fotouser = dados?.getString("imagem")

        binding.janela2.setOnClickListener {
            val Intent = Intent(this, Janela100x100cm::class.java)
            Intent.putExtra("nome", R.drawable.nomejanalvi)
            Intent.putExtra("piso", R.drawable.imagemjanela)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")

            startActivity(Intent)
        }

        binding.janela1.setOnClickListener {

            val Intent = Intent(this, Janela100x120cm::class.java)
            Intent.putExtra("nome", R.drawable.nomejanalvi)
            Intent.putExtra("piso", R.drawable.imagemjanela)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")
            startActivity(Intent)

        }

        binding.portaalumin1.setOnClickListener {

            val Intent = Intent(this, Porta210X60cm::class.java)
            Intent.putExtra("nome", R.drawable.nomeportaal)
            Intent.putExtra("piso", R.drawable.portafullalumin)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")
            startActivity(Intent)

        }

        binding.portaalumin2.setOnClickListener {

            val Intent = Intent(this, Porta210x70cm::class.java)
            Intent.putExtra("nome", R.drawable.nomeportaal)
            Intent.putExtra("piso", R.drawable.portafullalumin)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")
            startActivity(Intent)

        }

        binding.portaalumin3.setOnClickListener {

            val Intent = Intent(this, Porta210x80cmVi::class.java)
            Intent.putExtra("nome", R.drawable.nomeportaalvi)
            Intent.putExtra("piso", R.drawable.portaalumivi)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")
            startActivity(Intent)

        }

        binding.portamadeira1.setOnClickListener {

            val Intent = Intent(this, Porta210x70cmMA::class.java)
            Intent.putExtra("nome", R.drawable.nomeportamade)
            Intent.putExtra("piso", R.drawable.portamadeira1)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")
            startActivity(Intent)

        }

        binding.portamadeira2.setOnClickListener {

            val Intent = Intent(this, Porta210x60cmMA::class.java)
            Intent.putExtra("nome", R.drawable.nomeportamade)
            Intent.putExtra("piso", R.drawable.portamadeira1)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")
            startActivity(Intent)

        }

        binding.voltar2.setOnClickListener {
            finish()
        }


    }
}