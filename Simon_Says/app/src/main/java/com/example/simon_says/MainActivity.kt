package com.example.simon_says

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.simon_says.databinding.ActivityMainBinding
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val handler = Handler()
    private var esTurnoDeLaMaquina = true
    private var nivel = 1
    private var contador = 1
    private val botonesGenerados = ArrayList<ImageButton>()
    private val clicsJugador = ArrayList<ImageButton>()
    private var correcto = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Turno 1.Movimientos máquina, Turno 2.Repites movimientos", Toast.LENGTH_SHORT).show()

        binding.ibBlue.setOnClickListener {
            if (!esTurnoDeLaMaquina) {
                registrarClic(binding.ibBlue)
                verificarSecuencia()
            }

            binding.ibBlue.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))

            binding.ibBlue.postDelayed({
                binding.ibBlue.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkblue))
            }, 500)

        }

        binding.ibGreen.setOnClickListener {
            if (!esTurnoDeLaMaquina) {
                registrarClic(binding.ibGreen)
                verificarSecuencia()
            }

            binding.ibGreen.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))

            binding.ibGreen.postDelayed({
                binding.ibGreen.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgreen))
            }, 500)
        }

        binding.ibYellow.setOnClickListener {
            if (!esTurnoDeLaMaquina) {
                registrarClic(binding.ibYellow)
                verificarSecuencia()
            }

            binding.ibYellow.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))

            binding.ibYellow.postDelayed({
                binding.ibYellow.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkyellow))
            }, 500)
        }

        binding.ibRed.setOnClickListener {
            if (!esTurnoDeLaMaquina) {
                registrarClic(binding.ibRed)
                verificarSecuencia()
            }

            binding.ibRed.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))

            binding.ibRed.postDelayed({
                binding.ibRed.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkred))
            }, 500)
        }

        binding.btnSecuencia.setOnClickListener {
                secuenciaMaquina()
        }

        binding.btnReiniciar.setOnClickListener {
            nivel = 1
            botonesGenerados.clear()
            clicsJugador.clear()
            binding.txtLevel.text = nivel.toString()
            binding.btnSecuencia.visibility = Button.VISIBLE
        }
    }

    private fun secuenciaMaquina() {
        esTurnoDeLaMaquina = true
        botonesGenerados.clear()
        clicsJugador.clear()
        contador = 1
        binding.btnSecuencia.visibility = Button.INVISIBLE
        binding.btnReiniciar.visibility = Button.INVISIBLE

        val botones = mapOf(
            0 to binding.ibBlue,
            1 to binding.ibRed,
            2 to binding.ibYellow,
            3 to binding.ibGreen
        )

        val timer = Timer()


        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (contador <= nivel) {
                    val randomIndex = Random.nextInt(4)
                    val randomButton = botones[randomIndex]
                    runOnUiThread {
                        randomButton?.performClick()
                        if (randomButton != null) {
                            botonesGenerados.add(randomButton)
                        }
                    }
                    contador++
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "TU TURNO", Toast.LENGTH_SHORT).show()
                    }
                    timer.cancel()
                    esTurnoDeLaMaquina = false
                }
            }
        }, 0, 1000)
    }

    private fun registrarClic(boton: ImageButton) {
        clicsJugador.add(boton)
    }

    private fun verificarSecuencia() {
        if (clicsJugador.size == botonesGenerados.size) {
            correcto = true
            for (i in clicsJugador.indices) {
                if (clicsJugador[i] != botonesGenerados[i]) {
                    correcto = false
                    break
                }
            }
            if (correcto) {
                Toast.makeText(this, "ACERTASTE", Toast.LENGTH_SHORT).show()
                nivel++
                binding.txtLevel.text = nivel.toString()
                binding.btnSecuencia.visibility = Button.VISIBLE
                binding.btnReiniciar.visibility = Button.VISIBLE
            } else {
                Toast.makeText(this, "TE EQUIVOCASTE", Toast.LENGTH_SHORT).show()
                nivel = 1
                binding.txtLevel.text = nivel.toString()
                binding.btnSecuencia.visibility = Button.VISIBLE
                binding.btnReiniciar.visibility = Button.VISIBLE
            }
        }
    }
}
