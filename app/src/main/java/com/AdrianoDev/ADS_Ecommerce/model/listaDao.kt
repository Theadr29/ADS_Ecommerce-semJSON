package com.AdrianoDev.ADS_Ecommerce.model

import android.database.Cursor
import androidx.room.*

@Dao

interface listaDao {




    @Insert
    fun insert (lista: listadeprodutos)

    @Query("SELECT nome, fotoperfil FROM listadeprodutos")
    fun getNomeFoto(): Cursor

    @Query("SELECT * FROM listadeprodutos WHERE type = :type")
    fun getRegisterByType(type: String) : List<listadeprodutos>

    @Query("SELECT * FROM listadeprodutos")
    fun getALL() : List<listadeprodutos>

    @Query("SELECT SUM(valordopiso) FROM listadeprodutos")
    fun sumOfValorDoPiso(): Double

    @Query("DELETE FROM listadeprodutos")
    fun deleteAll(): Int


    @Delete()
    fun delete(lista: listadeprodutos): Int // FIXME: retorna 1 se deu sucesso


    @Update
    fun update(lista: listadeprodutos)

}