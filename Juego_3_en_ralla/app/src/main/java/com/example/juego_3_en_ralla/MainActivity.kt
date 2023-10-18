package com.example.juego_3_en_ralla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.juego_3_en_ralla.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var contador = 0

        fun modificarImagen(imageView: ImageView) {

            if (contador % 2 == 0) {
                imageView.setImageResource(R.drawable.cruz)
            } else {
                imageView.setImageResource(R.drawable.circulo)
            }
            contador++
            imageView.setOnClickListener(null)

            if (contador == 9) {
                binding.btnReinicio.visibility = Button.VISIBLE
            }
        }

        binding.iv11.setOnClickListener {
            modificarImagen(binding.iv11)
        }

        binding.iv12.setOnClickListener {
            modificarImagen(binding.iv12)
        }

        binding.iv13.setOnClickListener {
            modificarImagen(binding.iv13)
        }

        binding.iv21.setOnClickListener {
            modificarImagen(binding.iv21)
        }

        binding.iv22.setOnClickListener {
            modificarImagen(binding.iv22)
        }

        binding.iv23.setOnClickListener {
            modificarImagen(binding.iv23)
        }

        binding.iv31.setOnClickListener {
            modificarImagen(binding.iv31)
        }

        binding.iv32.setOnClickListener {
            modificarImagen(binding.iv32)
        }

        binding.iv33.setOnClickListener {
            modificarImagen(binding.iv33)
        }



        fun reinicio() {
            contador = 0
            binding.btnReinicio.visibility = Button.INVISIBLE
            binding.iv11.setImageResource(R.drawable.inicio)
            binding.iv12.setImageResource(R.drawable.inicio)
            binding.iv13.setImageResource(R.drawable.inicio)
            binding.iv21.setImageResource(R.drawable.inicio)
            binding.iv22.setImageResource(R.drawable.inicio)
            binding.iv23.setImageResource(R.drawable.inicio)
            binding.iv31.setImageResource(R.drawable.inicio)
            binding.iv32.setImageResource(R.drawable.inicio)
            binding.iv33.setImageResource(R.drawable.inicio)

            binding.iv11.setOnClickListener { modificarImagen(binding.iv11) }
            binding.iv12.setOnClickListener { modificarImagen(binding.iv12) }
            binding.iv13.setOnClickListener { modificarImagen(binding.iv13) }
            binding.iv21.setOnClickListener { modificarImagen(binding.iv21) }
            binding.iv22.setOnClickListener { modificarImagen(binding.iv22) }
            binding.iv23.setOnClickListener { modificarImagen(binding.iv23) }
            binding.iv31.setOnClickListener { modificarImagen(binding.iv31) }
            binding.iv32.setOnClickListener { modificarImagen(binding.iv32) }
            binding.iv33.setOnClickListener { modificarImagen(binding.iv33) }
        }

        binding.btnReinicio.setOnClickListener {
            reinicio()
        }
    }
}