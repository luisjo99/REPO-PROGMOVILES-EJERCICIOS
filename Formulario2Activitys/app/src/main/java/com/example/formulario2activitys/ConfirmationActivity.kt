package com.example.formulario2activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formulario2activitys.databinding.ActivityConfirmationBinding
import modelo.AlmacenUsuarios
import modelo.Usuario

class ConfirmationActivity : AppCompatActivity() {

    lateinit var binding: ActivityConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)
        binding= ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var nombre = intent.getStringExtra("nombre")
        var apellido = intent.getStringExtra("apellido")
        var dni = intent.getStringExtra("dni")
        var gmail = intent.getStringExtra("gmail")
        var contrasenia = intent.getStringExtra("contrasenia")

        binding.txtNombre2.setText(nombre)
        binding.txtApellido2.setText(apellido)
        binding.txtDni2.setText(dni)
        binding.txtGmail2.setText(gmail)

        var cadena: String =""
        var i:Int=1
        for (u in AlmacenUsuarios.usuarios){
            cadena+=" "+i+", "+u.nombre+" "+u.apellido+" "+u.dni+" "+u.gmail+" "+u.contrasenia+"\n"
            i++
            binding.edMultiline.setText(cadena)
        }

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}