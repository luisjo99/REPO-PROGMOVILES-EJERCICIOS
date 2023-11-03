package com.example.menu_multijuegos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.menu_multijuegos.databinding.ActivityVentanaOpcion1Binding

class VentanaOpcion1 : AppCompatActivity() {

    lateinit var binding: ActivityVentanaOpcion1Binding

    private var jugador = 0
    private var contaempate = 0
    private var ganadasjug1 = 0
    private var ganadasjug2 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_ventana_opcion1)
        binding = ActivityVentanaOpcion1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarOpcion1.title = "    MENU MULTIJUEGOS"
        binding.toolbarOpcion1.subtitle = "     TRES EN RAYA"
        binding.toolbarOpcion1.setLogo(R.drawable.ic_logo)

        var nombre = intent.getStringExtra("nombre")
        binding.textUsuario.setText(nombre)

        //aquí simplemente inflo la toolBaar, pero aún no hay opciones ni botón home.
        setSupportActionBar(binding.toolbarOpcion1)

        //en las siguientes líneas hago que aaprezca el botón de atrás (home) y genero su evento
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarOpcion1.setNavigationOnClickListener {
            Toast.makeText(this,"Pulsado el retroceso", Toast.LENGTH_SHORT).show()
            finish()
        }

        var contador = 0
        fun modificarImagen(imageView: ImageView) {
            if (contador % 2 == 0) {
                imageView.setImageResource(R.drawable.yas)
                binding.ivTurno.setImageResource(R.drawable.yone)
            } else {
                imageView.setImageResource(R.drawable.yone)
                binding.ivTurno.setImageResource(R.drawable.yas)
            }
            contador++
            contaempate++
            imageView.setOnClickListener(null)
        }

        binding.iv11.setOnClickListener {
            modificarImagen(binding.iv11)
            verificarGanador()
        }

        binding.iv12.setOnClickListener {
            modificarImagen(binding.iv12)
            verificarGanador()
        }

        binding.iv13.setOnClickListener {
            modificarImagen(binding.iv13)
            verificarGanador()
        }

        binding.iv21.setOnClickListener {
            modificarImagen(binding.iv21)
            verificarGanador()
        }

        binding.iv22.setOnClickListener {
            modificarImagen(binding.iv22)
            verificarGanador()
        }

        binding.iv23.setOnClickListener {
            modificarImagen(binding.iv23)
            verificarGanador()
        }

        binding.iv31.setOnClickListener {
            modificarImagen(binding.iv31)
            verificarGanador()
        }

        binding.iv32.setOnClickListener {
            modificarImagen(binding.iv32)
            verificarGanador()
        }

        binding.iv33.setOnClickListener {
            modificarImagen(binding.iv33)
            verificarGanador()
        }

        fun reinicio() {
            jugador = 0
            contaempate = 0
            binding.btnRePartida.visibility = Button.INVISIBLE
            binding.btnReinicio.visibility = Button.INVISIBLE
            binding.iv11.setImageResource(R.drawable.grieta)
            binding.iv12.setImageResource(R.drawable.grieta)
            binding.iv13.setImageResource(R.drawable.grieta)
            binding.iv21.setImageResource(R.drawable.grieta)
            binding.iv22.setImageResource(R.drawable.grieta)
            binding.iv23.setImageResource(R.drawable.grieta)
            binding.iv31.setImageResource(R.drawable.grieta)
            binding.iv32.setImageResource(R.drawable.grieta)
            binding.iv33.setImageResource(R.drawable.grieta)

            binding.iv11.setOnClickListener { modificarImagen(binding.iv11)
                verificarGanador()}
            binding.iv12.setOnClickListener { modificarImagen(binding.iv12)
                verificarGanador()}
            binding.iv13.setOnClickListener { modificarImagen(binding.iv13)
                verificarGanador()}
            binding.iv21.setOnClickListener { modificarImagen(binding.iv21)
                verificarGanador()}
            binding.iv22.setOnClickListener { modificarImagen(binding.iv22)
                verificarGanador()}
            binding.iv23.setOnClickListener { modificarImagen(binding.iv23)
                verificarGanador()}
            binding.iv31.setOnClickListener { modificarImagen(binding.iv31)
                verificarGanador()}
            binding.iv32.setOnClickListener { modificarImagen(binding.iv32)
                verificarGanador()}
            binding.iv33.setOnClickListener { modificarImagen(binding.iv33)
                verificarGanador()}
        }

        binding.btnReinicio.setOnClickListener {
            reinicio()
        }

        binding.btnRePartida.setOnClickListener {
            reinicio()
            ganadasjug1 = 0
            ganadasjug2 = 0
            binding.txtContador1.text = ganadasjug1.toString()
            binding.txtContador2.text = ganadasjug2.toString()
        }
    }

    fun verificarGanador() {
        val imagenYas = ContextCompat.getDrawable(this, R.drawable.yas)?.constantState
        val imagenYone = ContextCompat.getDrawable(this, R.drawable.yone)?.constantState

        val celdas = arrayOf(
            arrayOf(binding.iv11.drawable.constantState, binding.iv12.drawable.constantState, binding.iv13.drawable.constantState),
            arrayOf(binding.iv21.drawable.constantState, binding.iv22.drawable.constantState, binding.iv23.drawable.constantState),
            arrayOf(binding.iv31.drawable.constantState, binding.iv32.drawable.constantState, binding.iv33.drawable.constantState)
        )

        for (i in 0 until 3) {
            if (celdas[i][0] == imagenYas && celdas[i][1] == imagenYas && celdas[i][2] == imagenYas) {
                jugador = 1
            } else if (celdas[i][0] == imagenYone && celdas[i][1] == imagenYone && celdas[i][2] == imagenYone) {
                jugador = 2
            }

            if (celdas[0][i] == imagenYas && celdas[1][i] == imagenYas && celdas[2][i] == imagenYas) {
                jugador = 1
            } else if (celdas[0][i] == imagenYone && celdas[1][i] == imagenYone && celdas[2][i] == imagenYone) {
                jugador = 2
            }
        }

        if (celdas[0][0] == imagenYas && celdas[1][1] == imagenYas && celdas[2][2] == imagenYas) {
            jugador = 1
        } else if (celdas[0][0] == imagenYone && celdas[1][1] == imagenYone && celdas[2][2] == imagenYone) {
            jugador = 2
        }

        if (celdas[0][2] == imagenYas && celdas[1][1] == imagenYas && celdas[2][0] == imagenYas) {
            jugador = 1
        } else if (celdas[0][2] == imagenYone && celdas[1][1] == imagenYone && celdas[2][0] == imagenYone) {
            jugador = 2
        }

        if (jugador == 1) {
            Toast.makeText(this, "¡GANA YASUO!", Toast.LENGTH_SHORT).show()
            ganadasjug1++
            binding.txtContador1.text = ganadasjug1.toString()
            binding.btnReinicio.visibility = Button.VISIBLE
            binding.btnRePartida.visibility = Button.VISIBLE
            noClickable()
        } else if (jugador == 2) {
            ganadasjug2++
            Toast.makeText(this, "¡GANA YONE!", Toast.LENGTH_SHORT).show()
            binding.txtContador2.text = ganadasjug2.toString()
            binding.btnRePartida.visibility = Button.VISIBLE
            binding.btnReinicio.visibility = Button.VISIBLE
            noClickable()
        }else if (contaempate == 9 && jugador == 0) {
            binding.btnReinicio.visibility = Button.VISIBLE
            binding.btnRePartida.visibility = Button.VISIBLE
            Toast.makeText(this, "¡EMPATE!", Toast.LENGTH_SHORT).show()
        }
    }

    fun noClickable(){
        binding.iv11.setOnClickListener(null)
        binding.iv12.setOnClickListener(null)
        binding.iv13.setOnClickListener(null)
        binding.iv21.setOnClickListener(null)
        binding.iv22.setOnClickListener(null)
        binding.iv23.setOnClickListener(null)
        binding.iv31.setOnClickListener(null)
        binding.iv32.setOnClickListener(null)
        binding.iv33.setOnClickListener(null)
    }
}