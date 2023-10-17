package com.example.juego_3_en_ralla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.juego_3_en_ralla.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iv11.setImageResource(R.drawable.inicio)
        binding.iv12.setImageResource(R.drawable.inicio)
        binding.iv13.setImageResource(R.drawable.inicio)
        binding.iv21.setImageResource(R.drawable.inicio)
        binding.iv22.setImageResource(R.drawable.inicio)
        binding.iv23.setImageResource(R.drawable.inicio)
        binding.iv31.setImageResource(R.drawable.inicio)
        binding.iv32.setImageResource(R.drawable.inicio)
        binding.iv33.setImageResource(R.drawable.inicio)
    }
}