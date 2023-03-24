package com.AdrianoDev.ADS_Ecommerce.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.AdrianoDev.ADS_Ecommerce.model.listadeprodutos
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.request.RequestOptions
import com.AdrianoDev.ADS_Ecommerce.R
import com.squareup.picasso.LruCache
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Integer.min





class Adapter(private var produtos1: List<listadeprodutos> ) : RecyclerView.Adapter<Adapter.ReciclerviewCupom>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReciclerviewCupom {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.produtos, parent, false)
        return ReciclerviewCupom(view)


    }

    override fun onBindViewHolder(holder: ReciclerviewCupom, position: Int) {


        val item = produtos1 [position]

        holder.bind(item)
    }



    override fun getItemCount(): Int {


        return produtos1.size


    }

    inner class ReciclerviewCupom(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: listadeprodutos) {

            itemView.findViewById<ImageView>(R.id.imagemProduto).apply {

                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .override(40, 24) // definir o tamanho desejado da imagem
                    .dontAnimate()

                Glide.with(itemView.context)
                    .load(item.foto)
                    .apply(requestOptions)
                    .thumbnail(0.25f)
                    .into(this)
            }



            itemView.findViewById<ImageView>(R.id.nomeProduto).apply {

                val requestOptions = RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .skipMemoryCache(false)
                Glide.with(itemView.context)
                        .load(item.nome)
                        .apply(requestOptions)
                        .into(this)
            }



            itemView.findViewById<TextView>(R.id.quantidade).apply {
                text = item.quantidadeCx
            }

            itemView.findViewById<TextView>(R.id.qtdMetro).apply {
                text = item.quantidadeMt
            }

            itemView.findViewById<TextView>(R.id.valorPiso).apply {
                text = String.format("%.2f", item.valordopiso.toDouble()).replace(".", ",")
            }

            itemView.findViewById<TextView>(R.id.valorunidade).apply {
                text = item.valorUni
            }
        }
    }

    }