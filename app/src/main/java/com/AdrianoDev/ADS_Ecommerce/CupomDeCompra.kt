package com.AdrianoDev.ADS_Ecommerce


import org.apache.commons.lang3.math.NumberUtils

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.graphics.*
import java.io.FileOutputStream
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.*
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.*
import androidx.room.Room
import com.bumptech.glide.Glide
import com.AdrianoDev.ADS_Ecommerce.Adapter.Adapter
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityCupomDeCompraBinding
import com.AdrianoDev.ADS_Ecommerce.model.AppDataBase
import com.AdrianoDev.ADS_Ecommerce.model.listadeprodutos
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.itextpdf.text.*
import kotlinx.coroutines.*

import java.io.File

import java.text.SimpleDateFormat
import java.util.*


class CupomDeCompra() : AppCompatActivity() {


    private lateinit var context: Context
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var adapter: Adapter
    private lateinit var produtos: MutableList<listadeprodutos>
    private lateinit var binding: ActivityCupomDeCompraBinding
    private lateinit var recyclerView: RecyclerView
    var pageHeight = 4800
    var pageWidth = 2100
    var PERMISSION_CODE = 101


    override fun onCreate(savedInstanceState: Bundle?, ) {

        super.onCreate(savedInstanceState)


        binding = ActivityCupomDeCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gson = Gson()
        gson.fromJson(intent.getStringExtra("listaDeProdutos"), Array<listadeprodutos>::class.java)
        produtos = mutableListOf ()

        adapter = Adapter(produtos)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter



        val type = intent?.extras?.getString("type") ?: throw IllegalAccessError("type not found")

        CoroutineScope(Dispatchers.IO).launch {
            val app = application as App
            val dao = app.db.listDao()
            val response = dao.getRegisterByType(type)
            val limitedList = response.subList(0, minOf(response.size, 22)) // limita a lista em 22 itens
            withContext(Dispatchers.Main) {
                produtos.addAll(limitedList)
                adapter.notifyDataSetChanged()
                if (response.size > 22) {
                    Toast.makeText(this@CupomDeCompra, "Quantidade máxima de 21 itens atingida", Toast.LENGTH_SHORT).show() // exibe o toast informando que a quantidade máxima foi atingida
                }
            }
        }

        binding.voltar7.setOnClickListener {
            finish()
        }

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)
            val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)


            val dados = intent.extras

            val piso = dados?.getInt("caixas")

            var qtd = dados?.getDouble("qtdmetro")

            var valorpiso = dados?.getDouble("valorpiso")

            var imagemproduto = dados?.getInt("piso")

            var nome = dados?.getInt("nome")

            var updateId = dados?.getInt("updateID")

            var name = binding.comprador

            var imagem = binding.fotodeperfil

            if (account != null) {
                Glide.with(applicationContext).load(account.photoUrl).into(imagem)
                name.text = account.displayName

            } else {

            }

                listadeprodutos(
                    nome = nome!!,
                    foto = imagemproduto!!,
                    quantidadeCx = "$piso",
                    quantidadeMt = "$qtd",
                    valorUni = "",
                    valordopiso = "$valorpiso",
                    id = updateId!!,
                    type = "type",
                    photoUrl = "$imagem",
                    name = "$name"
                )


        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                val produtos1 = produtos[position]

                CoroutineScope(Dispatchers.IO).launch {
                    val db = Room.databaseBuilder(
                        applicationContext,
                        AppDataBase::class.java,
                        "ads ecommercce"
                    ).build()

                    db.listDao().delete(produtos1)

                    valorFinal()


                    db.close()


                    withContext(Dispatchers.Main) {
                        produtos.removeAt(position)
                        recyclerView.adapter?.notifyItemRemoved(position)

                    }

                }
            }

        }

        context = this


        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        valorFinal()

        if (checkPermissions()) {

        } else {

            requestPermission()
        }


        var enviando = false

        binding.enviar.setOnClickListener {
            if (!enviando) {
                enviando = true
                binding.enviar.visibility = View.GONE // torna o botão invisível
                binding.progressBar.visibility = View.VISIBLE // torna a ProgressBar visível

                CoroutineScope(Dispatchers.IO).launch {
                    val app = application as App
                    val dao = app.db.listDao()
                    val listaDeProdutos = dao.getALL()
                    gerarPNG()
                    withContext(Dispatchers.Main) {

                        Wattsapp()
                        binding.enviar.visibility = View.VISIBLE // torna o botão visível
                        binding.progressBar.visibility = View.INVISIBLE // torna a ProgressBar invisível

                    }

                        enviando = false
                    }

            }
        }
    }


    private fun valorFinal() {

        CoroutineScope(Dispatchers.IO).launch {

            val db = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java,
                "ads ecommercce"
            ).build()
            val soma = db.listDao().sumOfValorDoPiso()
            binding.valorfinal.text = String.format("%.2f", soma).replace('.', ',')

            db.close()


        }

        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                val currentDate = Calendar.getInstance().time

                val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                val formattedDate = dateFormat.format(currentDate)

                val hourFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val formattedHour = hourFormat.format(currentDate)

                binding.hora.text = formattedHour
                binding.data.text = formattedDate
            }
        }
        timer.scheduleAtFixedRate(task, 0, 1000)
    }

    fun checkPermissions(): Boolean {

        var writeStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            WRITE_EXTERNAL_STORAGE
        )


        var readStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            READ_EXTERNAL_STORAGE
        )


        return writeStoragePermission == PackageManager.PERMISSION_GRANTED
                && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }


    private fun gerarPNG() {

            val lista = mutableListOf<listadeprodutos>()

            val bitmapList = mutableListOf<Bitmap>()
            val bitmapList2 = mutableListOf<Bitmap>()
            val cabecalho = mutableListOf<Bitmap>()
            val quantidadeCxList = mutableListOf<String>()
            val quantidadeMtList = mutableListOf<String>()
            val valorUniList = mutableListOf<String>()
            val valordopisoList = mutableListOf<String>()
            val totallist = mutableListOf<Bitmap>()

            for (produto in produtos) {
                val nome = produto.nome
                val foto = produto.foto
                val quantidadeCx = produto.quantidadeCx
                val quantidadeMt = produto.quantidadeMt
                val valorUni = produto.valorUni
                val valordopiso = produto.valordopiso
                val id = produto.id
                val type = produto.type
                val photoUrl = produto.photoUrl
                val name = produto.name

                val item = listadeprodutos(
                    nome = nome,
                    foto = foto,
                    quantidadeCx = quantidadeCx,
                    quantidadeMt = quantidadeMt,
                    valorUni = valorUni,
                    valordopiso = valordopiso,
                    id = id,
                    type = type,
                    photoUrl = photoUrl,
                    name = name
                )
                lista.add(item)

                val imagemdoproduto = BitmapFactory.decodeResource(resources, foto)
                val scaledbmp1 = Bitmap.createScaledBitmap(imagemdoproduto, 80, 80, false)
                bitmapList.add(scaledbmp1)

                val nomeProduto = BitmapFactory.decodeResource(resources, nome)
                val scaledbmp2 = Bitmap.createScaledBitmap(nomeProduto, 210, 80, false)
                bitmapList2.add(scaledbmp2)

                val cabecalho1 = BitmapFactory.decodeResource(resources, R.drawable.cabecalho)
                val scaledbmp3 = Bitmap.createScaledBitmap(cabecalho1, 1500, 635, false)
                cabecalho.add(scaledbmp3)

                val total = BitmapFactory.decodeResource(resources, R.drawable.total)
                val scaledbmp4 = Bitmap.createScaledBitmap(total, 900, 200, false)
                totallist.add(scaledbmp4)

                quantidadeCxList.add(quantidadeCx)


                if (NumberUtils.isCreatable(produto.quantidadeMt)) {
                    quantidadeMtList.add(String.format("%.2f", produto.quantidadeMt.toDouble()))
                } else {
                    quantidadeMtList.add(produto.quantidadeMt)
                }

                valorUniList.add(valorUni)

                val formattedValordopiso = String.format("%.2f", valordopiso.toDouble())
                valordopisoList.add(formattedValordopiso)

                val bitmap = Bitmap.createBitmap(pageWidth, pageHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)

                var typeface = Typeface.create("masone", Typeface.NORMAL)

                val myColor = ResourcesCompat.getColor(resources, R.color.grena, null)
                val paint = Paint().apply {
                    color = myColor // Define a cor para o paint
                    textSize = 70F
                    typeface = typeface
                }

                val myColor4 = ResourcesCompat.getColor(resources, R.color.grena, null)
                val paint5 = Paint().apply {
                    color = myColor4 // Define a cor para o paint
                    textSize = 80F
                    typeface = typeface
                }

                val myColor2 = ResourcesCompat.getColor(resources, R.color.grena, null)
                val strokeColor = ResourcesCompat.getColor(resources, R.color.purple_777, null)
                var strokeWidth = 5F

                val paint2 = Paint().apply {
                    style = Paint.Style.FILL_AND_STROKE
                    color = myColor2
                    strokeJoin = Paint.Join.ROUND
                    strokeCap = Paint.Cap.ROUND
                    strokeWidth = strokeWidth
                    isAntiAlias = true
                }

                val rectF = RectF(0F, 0F, canvas.width.toFloat(), canvas.height.toFloat())
                paint2.style = Paint.Style.STROKE
                paint2.color = strokeColor
                canvas.drawRoundRect(rectF, 25F, 25F, paint2)
                paint2.style = Paint.Style.FILL
                canvas.drawRoundRect(rectF, 25F, 25F, paint2)

                canvas.drawBitmap(scaledbmp3, 300F, 325F, null)
                canvas.drawBitmap(scaledbmp4, 590F, 3300f, null)

                val valorFinal = binding.valorfinal.text.toString()
                canvas.drawText(valorFinal, 1060F, 3428F, paint5)


                val myColor3 = ResourcesCompat.getColor(resources, R.color.grena, null)
                val paint3 = Paint().apply {
                    color = myColor3 // Define a cor para o paint
                    textSize = 50F
                    typeface = typeface
                }

                val data = binding.data.text.toString()
                canvas.drawText(data, 1443F, 652F, paint3)

                val hora = binding.hora.text.toString()
                canvas.drawText(hora, 1443F, 822F, paint3)

                val fotoperfil = binding.fotodeperfil
                val paint4 = Paint()

                val nomeusuario = binding.comprador.text
                canvas.drawText("$nomeusuario", 520F, 820F, paint3)

                val bitmap1 = Bitmap.createBitmap(
                    fotoperfil.width,
                    fotoperfil.height,
                    Bitmap.Config.ARGB_8888
                )
                val canvas1 = Canvas(bitmap1)
                canvas1.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                fotoperfil.draw(canvas1)

                canvas.drawBitmap(bitmap1, 400F, 495F, paint4)

                for (i in lista.indices) {
                    canvas.drawBitmap(bitmapList[i], 300F, 983F + (i * 100), null)
                    canvas.drawBitmap(bitmapList2[i], 400F, 983F + (i * 100), null)
                    canvas.drawText(quantidadeCxList[i], 720F, 1053F + (i * 100), paint)
                    canvas.drawText(quantidadeMtList[i], 935F, 1055F + (i * 100), paint)
                    canvas.drawText(valorUniList[i], 1235F, 1055F + (i * 100), paint)
                    canvas.drawText(valordopisoList[i], 1557F, 1055F + (i * 100), paint)
                }

                val file: File = File(context.getExternalFilesDir(null), "Seu Orçamento.JPG")

                try {

                    val outputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
                    outputStream.flush()
                    outputStream.close()
                }  catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }

    fun requestPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), PERMISSION_CODE
        )
    }

  private  fun Wattsapp(){



            val file: File = File(context.getExternalFilesDir(null), "Seu Orçamento.JPG")

            // Código para compartilhar a imagem gerada
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setPackage("com.whatsapp")
            shareIntent.putExtra(
                Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(
                    context,
                    "com.AdrianoDev.ADS_Ecommerce.fileprovider", file
                )
            )
            shareIntent.type = "image/jpeg"
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            shareIntent.putExtra(
                Intent.EXTRA_PHONE_NUMBER,
                ""
            )


            if (context.packageManager.resolveActivity(shareIntent, 0) != null) {
                startActivity(
                    context,
                    Intent.createChooser(shareIntent, "Compartilhar via"),
                    null
                )
            } else {
                Toast.makeText(
                    context,
                    "WhatsApp não está instalado no dispositivo",
                    Toast.LENGTH_LONG
                ).show()
            }

    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (requestCode == PERMISSION_CODE) {


            if (grantResults.size > 0) {


                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED) {

                } else {
                    finish()
                }
            }
        }
    }


}
