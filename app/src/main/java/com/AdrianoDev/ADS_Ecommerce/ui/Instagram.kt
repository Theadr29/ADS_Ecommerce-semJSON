package com.AdrianoDev.ADS_Ecommerce.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.AdrianoDev.ADS_Ecommerce.databinding.FragmentSlideshowBinding


class Instagram : AppCompatActivity() {

    private lateinit var binding: FragmentSlideshowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSlideshowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.voltar3.setOnClickListener {
            finish()
        }


        binding.insta.setOnClickListener {
            val uri = Uri.parse("https://www.instagram.com/ads_ecommerce/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.instagram.android")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/ads_ecommerce/")))
            }
        }


    }
}