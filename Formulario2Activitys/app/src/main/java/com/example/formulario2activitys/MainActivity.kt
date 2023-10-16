package com.example.formulario2activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.formulario2activitys.databinding.ActivityMainBinding
import modelo.AlmacenUsuarios
import modelo.Usuario

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.edNombre.text.toString()
            val apellido = binding.edApellido.text.toString()
            val dni = binding.edDni.text.toString()
            val gmail = binding.edGmail.text.toString()
            val contrasenia = binding.edContrasenia.text.toString()
            val confirmacion = binding.edConfirmacion.text.toString()
            var usuario: Usuario = Usuario(nombre, apellido, dni, gmail, contrasenia)

            var dnimulti: String =""
            var i:Int=1
            for (u in AlmacenUsuarios.usuarios){
                if (u.dni == dni){
                    dnimulti=dni
                }
                i++
            }

            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || gmail.isEmpty() || contrasenia.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos para poder registrate", Toast.LENGTH_SHORT).show()
            } else if (contrasenia.length < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            } else if (!contrasenia.equals(confirmacion)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }else if (dni==dnimulti){
                    Toast.makeText(this, "El usuario ya está registrado, DNI utilizado", Toast.LENGTH_SHORT).show()
            } else {
                AlmacenUsuarios.aniadirUsuario(usuario)
                cambiarVentana()
            }
        }
    }
        fun cambiarVentana() {
            var miIntent: Intent = Intent(this, ConfirmationActivity::class.java)
            miIntent.putExtra("nombre", binding.edNombre.text.toString())
            miIntent.putExtra("apellido", binding.edApellido.text.toString())
            miIntent.putExtra("dni", binding.edDni.text.toString())
            miIntent.putExtra("gmail", binding.edGmail.text.toString())
            miIntent.putExtra("contrasenia", binding.edContrasenia.text.toString())
            startActivity(miIntent)
        }
}
