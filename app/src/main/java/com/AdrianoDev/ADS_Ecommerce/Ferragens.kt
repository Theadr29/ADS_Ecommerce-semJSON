package com.AdrianoDev.ADS_Ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityFerragensBinding
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.FerragensProntas
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.MateriaisFerragens
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Trelica
import com.AdrianoDev.ADS_Ecommerce.Calculadoras.Varasdeferro


class Ferragens : AppCompatActivity() {

    private lateinit var binding: ActivityFerragensBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ferragens)



        binding = ActivityFerragensBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.voltar2.setOnClickListener {
            finish()
        }
        binding.ferragempronta.setOnClickListener {
            val Intent = Intent(this, FerragensProntas::class.java)
            Intent.putExtra("nome",R.drawable.nomeferrapronta)
            Intent.putExtra("piso",R.drawable.imagemferragempronta)
            startActivity(Intent)
        }

        binding.varadeferro.setOnClickListener {
            val Intent = Intent(this, Varasdeferro::class.java)
            Intent.putExtra("nome",R.drawable.nomevaraferro)
            Intent.putExtra("piso",R.drawable.imagemvaras)
            startActivity(Intent)
        }

        binding.trelica.setOnClickListener {
            val Intent = Intent(this, Trelica ::class.java)
            Intent.putExtra("nome",R.drawable.nometrelica)
            Intent.putExtra("piso",R.drawable.imagemtrelica)
            startActivity(Intent)
        }

        binding.materferragem.setOnClickListener {
            val Intent = Intent(this, MateriaisFerragens ::class.java)

            startActivity(Intent)
        }

    }
}