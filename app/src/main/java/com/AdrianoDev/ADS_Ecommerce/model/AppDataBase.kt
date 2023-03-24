package com.AdrianoDev.ADS_Ecommerce.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities =[listadeprodutos::class], version = 1)
@TypeConverters(DateConverter::class)

abstract class AppDataBase : RoomDatabase () {

    abstract fun listDao(): listaDao

    companion object {

        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {
           return if (INSTANCE == null) {

                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java, "ads ecommercce"
                    ).build()

                }
                INSTANCE as AppDataBase

            } else {

                INSTANCE as AppDataBase
            }

        }

    }
}