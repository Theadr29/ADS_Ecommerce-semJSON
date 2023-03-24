package com.AdrianoDev.ADS_Ecommerce

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityMateriaisLajesBinding
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Escoras
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Isopor
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Lajes
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Malhas

class MateriaisLajes : AppCompatActivity() {
    private lateinit var binding: ActivityMateriaisLajesBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMateriaisLajesBinding.inflate(layoutInflater)


        setContentView(binding.root)

        val dados = intent.extras


        var name = dados?.getString("user")

        var fotouser = dados?.getString("imagem")



        binding.lajes.setOnClickListener {
            val Intent = Intent(this, Lajes::class.java)
            Intent.putExtra("nome", R.drawable.nomelajes)
            Intent.putExtra("piso", R.drawable.imagemlajes)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")

            startActivity(Intent)
        }

        binding.escoras.setOnClickListener {
            val Intent = Intent(this, Escoras::class.java)
            Intent.putExtra("nome", R.drawable.nomeescoras)
            Intent.putExtra("piso", R.drawable.imagemescoras)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")

            startActivity(Intent)
        }

        binding.isopor.setOnClickListener {
            val Intent = Intent(this, Isopor::class.java)
            Intent.putExtra("nome", R.drawable.nomeisopor)
            Intent.putExtra("piso", R.drawable.imagemisopor)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")

            startActivity(Intent)
        }

        binding.malha.setOnClickListener {
            val Intent = Intent(this, Malhas::class.java)
            Intent.putExtra("nome", R.drawable.nomemalha)
            Intent.putExtra("piso", R.drawable.imagemmalha)
            Intent.putExtra("user", "$name")
            Intent.putExtra("imagem","$fotouser")

            startActivity(Intent)
        }



        binding.voltar2.setOnClickListener {
            finish()

        }
    }
}
