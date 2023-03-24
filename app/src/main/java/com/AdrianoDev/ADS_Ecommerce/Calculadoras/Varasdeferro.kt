package com.AdrianoDev.ADS_Ecommerce.Calculadoras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AdrianoDev.ADS_Ecommerce.databinding.ActivityVarasdeferroBinding

class Varasdeferro : AppCompatActivity() {

    private lateinit var binding: ActivityVarasdeferroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVarasdeferroBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}