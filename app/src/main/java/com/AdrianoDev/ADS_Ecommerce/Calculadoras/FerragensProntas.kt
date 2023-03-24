package com.AdrianoDev.ADS_Ecommerce.Calculadoras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.AdrianoDev.ADS_Ecommerce.App
import com.AdrianoDev.ADS_Ecommerce.model.listadeprodutos
import com.AdrianoDev.ADS_Ecommerce.CupomDeCompra
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityFerragensProntasBinding

class FerragensProntas : AppCompatActivity() {

    private lateinit var binding:ActivityFerragensProntasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFerragensProntasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextNumber.minValue = 0
        binding.editTextNumber.maxValue = 100



        binding.editTextNumber.setOnValueChangedListener { _, _, _ ->
            calculadora()


        }


        binding.voltar3.setOnClickListener {
            finish()
        }


    }


    private fun calculadora() {


        val mtpiso = binding.editTextNumber.value.toDouble()


        val resultado = mtpiso.toString().toDouble() * 45.00
        binding.resultadofinal.text = resultado.toString()


        val dados = intent.extras
        var piso = dados?.getInt("piso")
        var nome = dados?.getInt("nome")

        var name = dados?.getString("user")

        var fotouser = dados?.getString("")

        val updateId = intent.extras?.getInt("updateId")

        binding.comprar.setOnClickListener {

            if (resultado == 0.0) {
                // Show an error message to the user or prevent submission in some other way
                Toast.makeText(this, "Por favor, selecione uma quantidade válida.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Thread {


                val app = application as App
                val dao = app.db.listDao()


                dao.insert(

                    listadeprodutos(
                        nome = nome!!,
                        foto = piso!!,
                        quantidadeCx = "$mtpiso",
                        quantidadeMt = "",
                        valorUni = "45,00",
                        valordopiso = "$resultado",
                        id = updateId!!,
                        type = "type",
                        name = "$name",
                        photoUrl = "$fotouser"


                    )
                )
                runOnUiThread {
                    val Intent = Intent(this, CupomDeCompra::class.java)
                    Intent.putExtra("caixas", mtpiso)
                    Intent.putExtra("valorpiso", resultado)
                    Intent.putExtra("piso", piso)
                    Intent.putExtra("nome", nome)
                    Intent.putExtra("type", "type")
                    Intent.putExtra("name", "$name")
                    Intent.putExtra("photoUri", "$fotouser")
                    startActivity(Intent)
                }

            }.start()


        }


    }
}