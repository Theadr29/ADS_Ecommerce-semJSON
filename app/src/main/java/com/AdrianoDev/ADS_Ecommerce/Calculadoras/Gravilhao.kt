package com.AdrianoDev.ADS_Ecommerce.Calculadoras

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import com.AdrianoDev.ADS_Ecommerce.App
import com.AdrianoDev.ADS_Ecommerce.CupomDeCompra
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityGravilhaoBinding
import com.AdrianoDev.ADS_Ecommerce.model.listadeprodutos

class Gravilhao : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private lateinit var binding: ActivityGravilhaoBinding

    private val decimalValues = floatArrayOf(0.0f,0.5f, 1.0F, 1.5f, 2.0f, 2.5f, 3.0f, 3.5f, 4.0f, 4.5f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGravilhaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val displayedValues = Array(decimalValues.size) { i -> String.format("%.1f", decimalValues[i]) }

        binding.editTextNumber.minValue = 0
        binding.editTextNumber.maxValue = decimalValues.size - 1
        binding.editTextNumber.displayedValues = displayedValues
        binding.editTextNumber.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        binding.editTextNumber.setFormatter { value -> String.format("%.1f", decimalValues[value]) }



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

        val selectedValue = decimalValues[mtpiso]
        val qtddemetro = selectedValue * 50
        qtddemetro.toInt()

        val resultado = selectedValue * 180.00
        binding.resultadofinal.text = resultado.toString()


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
                        quantidadeCx = "$selectedValue",
                        quantidadeMt = "$qtddemetro",
                        valorUni = "180,00",
                        valordopiso = "$resultado",
                        id = updateId!!,
                        type = "type",
                        photoUrl= "$fotouser",
                        name = "$name"
                    )
                )
                runOnUiThread {
                    val Intent = Intent(this, CupomDeCompra::class.java)
                    Intent.putExtra("caixas", selectedValue)
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
