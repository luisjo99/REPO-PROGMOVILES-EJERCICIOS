package com.example.recyclerjuegolss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerjuegolss.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}