package com.AdrianoDev.ADS_Ecommerce

import android.app.Application
import com.AdrianoDev.ADS_Ecommerce.model.AppDataBase

class App : Application() {

    lateinit var db: AppDataBase

    override fun onCreate() {
        super.onCreate()
        db = AppDataBase.getDataBase(this)
    }

}
