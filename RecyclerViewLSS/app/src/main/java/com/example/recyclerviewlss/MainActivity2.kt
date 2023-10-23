package com.example.recyclerviewlss

import Modelo.Personaje
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewlss.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main2)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var p  = intent.getSerializableExtra("obj") as Personaje
        binding.txtPersona2.text = p.toString()

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}