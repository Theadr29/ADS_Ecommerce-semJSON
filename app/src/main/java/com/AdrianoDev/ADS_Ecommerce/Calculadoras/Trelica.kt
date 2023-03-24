package com.AdrianoDev.ADS_Ecommerce.Calculadoras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityTrelicaBinding

class Trelica : AppCompatActivity() {

    private lateinit var binding: ActivityTrelicaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrelicaBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}