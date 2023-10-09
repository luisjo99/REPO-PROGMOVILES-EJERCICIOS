package com.example.variosactivityspasodatos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.variosactivityspasodatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boton.setOnClickListener {
            cambiarVentana()
        }

    }

    fun cambiarVentana(){
        var miIntent: Intent = Intent(this, Ventana2::class.java)
        startActivity(miIntent)
    }
}

