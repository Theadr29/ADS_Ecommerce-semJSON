package com.AdrianoDev.ADS_Ecommerce.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.AdrianoDev.ADS_Ecommerce.databinding.FragmentGalleryBinding


class SobreADS : AppCompatActivity() {

    private lateinit var binding: FragmentGalleryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.voltar3.setOnClickListener {
            finish()
        }

    }
}