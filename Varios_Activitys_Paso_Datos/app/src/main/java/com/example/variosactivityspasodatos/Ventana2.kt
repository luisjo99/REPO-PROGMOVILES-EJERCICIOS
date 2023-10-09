package com.example.variosactivityspasodatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.variosactivityspasodatos.databinding.ActivityMainBinding
import com.example.variosactivityspasodatos.databinding.ActivityVentana2Binding
import modelo.AlmacenPersonas
import modelo.Persona

class Ventana2 : AppCompatActivity() {

    lateinit var binding: ActivityVentana2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana2)
        binding= ActivityVentana2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var nombre = intent.getStringExtra("nombre")
        var edad = intent.getStringExtra("edad")
        var persona: Persona = Persona(nombre,edad)

        binding.cajaNombre.setText(nombre)
        binding.cajaEdad.setText(edad)

        AlmacenPersonas.aniadirPersona(persona)
        var cadena: String =""
        var i:Int=1
        for (p in AlmacenPersonas.personas){
            cadena+=" "+i+", "+p.nombre+" "+p.edad+"\n"
            i++
            binding.multiLine.setText(cadena)
        }

        binding.boton.setOnClickListener {
            finish()
        }
    }
}