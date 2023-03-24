package com.AdrianoDev.ADS_Ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityPisosBinding
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.ItemPiso
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Revestimento


class Pisos : AppCompatActivity() {
    private lateinit var binding: ActivityPisosBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPisosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.voltar.setOnClickListener {

         finish()

        }

        val dados = intent.extras

        var name = dados?.getString("user")

        var fotouser = dados?.getString("imagem")


        val pisobranco = findViewById<ImageButton>(R.id.ButtonPisoBranco)
        pisobranco.setOnClickListener {
            val itemPiso =Intent(this, ItemPiso::class.java)
            itemPiso.putExtra("piso", R.drawable.pisobranco)
            itemPiso.putExtra("descricao", R.drawable.descricao_branco)
            itemPiso.putExtra("nome", R.drawable.cupompisobranco)
            itemPiso.putExtra("user", "$name")
            itemPiso.putExtra("imagem","$fotouser")

            startActivity(itemPiso)


        }
        val pisocreme = findViewById<ImageButton>(R.id.ButtonPisoCreme)
        pisocreme.setOnClickListener {
            val itemPiso =Intent(this, ItemPiso::class.java)
            itemPiso.putExtra("piso", R.drawable.pisocreme)
            itemPiso.putExtra("descricao", R.drawable.descricao_creme)
            itemPiso.putExtra("nome", R.drawable.cupompisocreme)
            itemPiso.putExtra("user", "$name")
            itemPiso.putExtra("imagem","$fotouser")

            startActivity(itemPiso)


        }
        val pisoverdebran = findViewById<ImageButton>(R.id.ButtonPisoVerde_Bran)
        pisoverdebran.setOnClickListener {
            val itemPiso = Intent(this, ItemPiso::class.java)
            itemPiso.putExtra("piso", R.drawable.pisoverdebranco)
            itemPiso.putExtra("descricao", R.drawable.descricao_ver_bran)
            itemPiso.putExtra("nome", R.drawable.cupompisoverbran)
            itemPiso.putExtra("user", "$name")
            itemPiso.putExtra("imagem","$fotouser")

            startActivity(itemPiso)
        }


        val amadeirado = findViewById<ImageButton>(R.id.ButtonPisoAmadeirado)
        amadeirado.setOnClickListener {

            val itemPiso = Intent(this, ItemPiso::class.java)
            itemPiso.putExtra("piso", R.drawable.pisoamadeirado)
            itemPiso.putExtra("descricao", R.drawable.descricao_amadeirado)
            itemPiso.putExtra("nome", R.drawable.cupompisoamadeirado)
            itemPiso.putExtra("user", "$name")
            itemPiso.putExtra("imagem","$fotouser")

            startActivity(itemPiso)

        }

        val pisoareia = findViewById<ImageButton>(R.id.ButtonPisoAreia)
        pisoareia.setOnClickListener {
            val itemPiso = Intent(this, ItemPiso::class.java)
            itemPiso.putExtra("piso", R.drawable.pisoareia)
            itemPiso.putExtra("descricao", R.drawable.descricao_areia)
            itemPiso.putExtra("nome", R.drawable.cupompisoareia)
            itemPiso.putExtra("user", "$name")
            itemPiso.putExtra("imagem","$fotouser")

            startActivity(itemPiso)
        }

        val revestQuaBranco = findViewById<ImageButton>(R.id.ButtonRevestQua_Bran)
        revestQuaBranco.setOnClickListener {
            val itemPiso = Intent(this, Revestimento::class.java)
            itemPiso.putExtra("piso", R.drawable.pisoquadri)
            itemPiso.putExtra("descricao", R.drawable.descricao_revestquadribran)
            itemPiso.putExtra("nome", R.drawable.cupompisorevestquadri)
            itemPiso.putExtra("user", "$name")
            itemPiso.putExtra("imagem","$fotouser")

            startActivity(itemPiso)
        }



    }
}

