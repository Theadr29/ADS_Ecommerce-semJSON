package com.AdrianoDev.ADS_Ecommerce.Calculadoras

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import com.AdrianoDev.ADS_Ecommerce.App
import com.AdrianoDev.ADS_Ecommerce.CupomDeCompra
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityBlocosBinding
import com.AdrianoDev.ADS_Ecommerce.model.listadeprodutos

class Blocos : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private lateinit var binding: ActivityBlocosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlocosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextNumber.minValue = 0
        binding.editTextNumber.maxValue = 7
        binding.editTextNumber.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        binding.editTextNumber.setOnValueChangedListener { _, _, _ ->
            calculadora()

        }

        calculadora()

        binding.voltar3.setOnClickListener {
            finish()
        }

    }




    private fun calculadora() {


        val mtpiso = binding.editTextNumber.value

        val resultado = mtpiso.toString().toDouble() * 850.00
        binding.resultadofinal.text = resultado.toString()

        val resultado2 = mtpiso * 1.000


        val dados = intent.extras

        var piso = dados?.getInt("piso")
        var nome = dados?.getInt("nome")


        val updateId = intent.extras?.getInt("updateId")

        var name = dados?.getString("user")

        var fotouser = dados?.getString("imagem")

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
                        quantidadeMt = "$resultado2",
                        valorUni = "850,00",
                        valordopiso = "$resultado",
                        id = updateId!!,
                        type = "type",
                        photoUrl= "$fotouser",
                        name = "$name"

                    )
                )
                runOnUiThread {
                    val Intent = Intent(this, CupomDeCompra::class.java)
                    Intent.putExtra("caixas", mtpiso)
                    Intent.putExtra("valorpiso", resultado)
                    Intent.putExtra("piso", piso)
                    Intent.putExtra("nome", nome)
                    Intent.putExtra("type", "type")
                    Intent.putExtra("user", "$name")
                    Intent.putExtra("imagem","$fotouser")
                    startActivity(Intent)

                }


            }.start()

        }
    }
}