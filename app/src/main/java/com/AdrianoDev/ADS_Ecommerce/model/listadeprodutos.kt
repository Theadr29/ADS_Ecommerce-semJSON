package com.AdrianoDev.ADS_Ecommerce.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class listadeprodutos(

    @PrimaryKey (autoGenerate = true) var id: Int = 0,

    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "nome") var nome: Int,

    @ColumnInfo (name = "foto") var foto: Int,

    @ColumnInfo(name = "quantidadecx") var quantidadeCx: String,

    @ColumnInfo(name = "name") var name: String,

    @ColumnInfo(name = "fotoperfil") var photoUrl : String,

    @ColumnInfo(name = "quantidademt") var quantidadeMt: String,

    @ColumnInfo(name = "valordoitem") var valorUni: String,

    @ColumnInfo(name = "valordopiso") var valordopiso: String,


    @ColumnInfo(name = "created_date") var createdDate: Date = Date(),
)
