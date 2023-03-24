package com.AdrianoDev.ADS_Ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityMineraisBinding
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Areia
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Blocos
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Cimento
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Gravilhao


class Minerais : AppCompatActivity() {

    private lateinit var binding: ActivityMineraisBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minerais)


        binding = ActivityMineraisBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dados = intent.extras


        var name = dados?.getString("user")

        var fotouser = dados?.getString("imagem")

        binding.voltar2.setOnClickListener {
            finish()
        }

        binding.areia.setOnClickListener {
            val Areia = Intent(this, com.AdrianoDev.ADS_Ecommerce.Calculadoras.Areia::class.java)
            Areia.putExtra("piso", R.drawable.imagemareia)
            Areia.putExtra("nome", R.drawable.nomeareia)
            Areia.putExtra("user", "$name")
            Areia.putExtra("imagem","$fotouser")
            startActivity(Areia)

        }

        binding.gravilhao.setOnClickListener {

            val Gravilhao = Intent(this, Gravilhao::class.java)
            Gravilhao.putExtra("piso", R.drawable.imagemgravilhao)
            Gravilhao.putExtra("nome", R.drawable.nomegravilhao)
            Gravilhao.putExtra("user", "$name")
            Gravilhao.putExtra("imagem","$fotouser")
            startActivity(Gravilhao)
        }

        binding.blocos.setOnClickListener {

            val Blocos = Intent(this, Blocos::class.java)
            Blocos.putExtra("piso", R.drawable.imagembloco)
            Blocos.putExtra("nome", R.drawable.nomeblocos)
            Blocos.putExtra("user", "$name")
            Blocos.putExtra("imagem","$fotouser")
            startActivity(Blocos)

        }

        binding.cimento.setOnClickListener {

            val Cimento = Intent(this, Cimento::class.java)
            Cimento.putExtra("piso", R.drawable.imagemcimento)
            Cimento.putExtra("nome", R.drawable.nomecimento)
            Cimento.putExtra("user", "$name")
            Cimento.putExtra("imagem","$fotouser")
            startActivity(Cimento)
        }

        //     binding.ferragens.setOnClickListener {

        //        val Cimento = Intent(this,Ferragens::class.java)
        //       Cimento.putExtra("piso", R.drawable.imagemcimento)
        //     Cimento.putExtra("nome", R.drawable.nomecimento)

        //    startActivity(Cimento)
        // }


    }

}