package com.example.playsnapui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playsnapui.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanObject.setOnClickListener{
            val explicit = Intent(this, SearchByCameraActivity::class.java)

            startActivity(explicit)
        }
    }
}