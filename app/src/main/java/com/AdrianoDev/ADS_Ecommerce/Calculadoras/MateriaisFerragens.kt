package com.AdrianoDev.ADS_Ecommerce.Calculadoras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityMateriaisFerragensBinding

class MateriaisFerragens : AppCompatActivity() {


    private lateinit var binding: ActivityMateriaisFerragensBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMateriaisFerragensBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}