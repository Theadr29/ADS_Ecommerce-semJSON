package com.AdrianoDev.ADS_Ecommerce.Calculadoras

import android.content.Intent
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.AdrianoDev.ADS_Ecommerce.App
import com.AdrianoDev.ADS_Ecommerce.model.listadeprodutos
import com.AdrianoDev.ADS_Ecommerce.CupomDeCompra
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityRevestimentoBinding


class Revestimento : AppCompatActivity() {

    private lateinit var binding: ActivityRevestimentoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityRevestimentoBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.editTextNumber.minValue = 0
        binding.editTextNumber.maxValue = 30
        binding.editTextNumber.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        binding.textView2.maxLines = 1

        binding.editTextNumber.setOnValueChangedListener { _, _, _ ->
            calculadora()


        }

        calculadora()

        binding.voltar3.setOnClickListener {
            finish()
        }


        val dados = intent.extras

        var piso = dados?.getInt("piso")
        binding.imagemProduto.setImageResource(piso!!)

        var descricao = dados?.getInt("descricao")
        binding.descricao.setImageResource(descricao!!)

        val mtpiso = binding.editTextNumber.value

        val qtddemetro = mtpiso.toDouble() * 2.68
        binding.textView2.text = qtddemetro.toString()

        val multipli = qtddemetro

        val resultado2 = multipli.toString().toDouble() * 33.00
        binding.resultadofinal.text = resultado2.toString()




    }


    private fun calculadora() {


        val mtpiso = binding.editTextNumber.value

        val qtddemetro = mtpiso.toDouble() * 2.44
        binding.textView2.text = qtddemetro.toString()

        val multipli = qtddemetro

        val resultado = multipli.toString().toDouble() * 33.00
        binding.resultadofinal.text = resultado.toString()


        val dados = intent.extras

        val piso = dados?.getInt("piso")

        val descricao = dados?.getInt("descricao")

        val nome = dados?.getInt("nome")


        val updateId = intent.extras?.getInt("updateId")

        val name = dados?.getString("user")

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
                        quantidadeMt = "$qtddemetro",
                        valorUni = "33,00",
                        valordopiso = "$resultado",
                        id = updateId!!,
                        type = "type",
                        photoUrl = "$fotouser",
                        name = "$name"
                    )
                )
                runOnUiThread {
                    val Intent = Intent(this, CupomDeCompra::class.java)
                    Intent.putExtra("caixas", mtpiso)
                    Intent.putExtra("qtdmetro", qtddemetro)
                    Intent.putExtra("valorpiso", resultado)
                    Intent.putExtra("piso", piso)
                    Intent.putExtra("descricao", descricao)
                    Intent.putExtra("nome", nome)
                    Intent.putExtra("type", "type")

                    Intent.putExtra("user", "$name")
                    Intent.putExtra("imagem", "$fotouser")
                    startActivity(Intent)

                }

            }.start()


        }


    }
}