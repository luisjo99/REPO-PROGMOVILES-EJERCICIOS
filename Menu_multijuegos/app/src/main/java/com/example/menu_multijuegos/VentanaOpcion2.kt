package com.example.menu_multijuegos

import Auxiliar.Conexion
import Modelo.Almacen
import Modelo.Persona
import android.content.DialogInterface
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.menu_multijuegos.databinding.ActivityVentanaOpcion1Binding
import com.example.menu_multijuegos.databinding.ActivityVentanaOpcion2Binding
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class VentanaOpcion2 : AppCompatActivity() {

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Has pulsado sí", Toast.LENGTH_SHORT).show()
    }

    lateinit var binding: ActivityVentanaOpcion2Binding

    private var esTurnoDeLaMaquina = true
    private var nivel = 1
    private var contador = 1
    private val botonesGenerados = ArrayList<ImageButton>()
    private val clicsJugador = ArrayList<ImageButton>()
    private var correcto = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_ventana_opcion1)
        binding = ActivityVentanaOpcion2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarOpcion2.title = "    MENU MULTIJUEGOS"
        binding.toolbarOpcion2.subtitle = "     SIMON SAYS"
        binding.toolbarOpcion2.setLogo(R.drawable.ic_logo)

        var nombre = intent.getStringExtra("nombre")
        binding.tvUsuario.setText(nombre)

        val jugadorExistente = Conexion.buscarPersona(this, binding.tvUsuario.text.toString())
        if (jugadorExistente != null) {
            // Jugador existente, mostrar mensaje con el nivel máximo
            Toast.makeText(
                this,
                "¡Bienvenido de nuevo, ${jugadorExistente.nombre}! Tu máximo nivel anterior: ${jugadorExistente.maxnivel}",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            // Nuevo jugador, mostrar saludo
            Toast.makeText(this, "¡Bienvenido, ${binding.tvUsuario.text}!", Toast.LENGTH_SHORT).show()
        }

        //aquí simplemente inflo la toolBaar, pero aún no hay opciones ni botón home.
        setSupportActionBar(binding.toolbarOpcion2)

        //en las siguientes líneas hago que aaprezca el botón de atrás (home) y genero su evento
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarOpcion2.setNavigationOnClickListener {
            Toast.makeText(this, "Pulsado el retroceso", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Toast.makeText(this, "Turno 1.Movimientos máquina, Turno 2.Repites movimientos", Toast.LENGTH_SHORT).show()

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
                        Toast.makeText(this@VentanaOpcion2, "TU TURNO", Toast.LENGTH_SHORT).show()
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
                buscarPersona()
                nivel = 1
                binding.txtLevel.text = nivel.toString()
                binding.btnSecuencia.visibility = Button.VISIBLE
                binding.btnReiniciar.visibility = Button.VISIBLE
            }
        }
    }

    fun addPersona() {
        if (binding.tvUsuario.text.toString().trim().isEmpty()){
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("ERROR")
                setMessage("INTRODUCE NOMBRE DE USUARIO")
                setPositiveButton("Aceptar", DialogInterface.OnClickListener(positiveButtonClick))
                show()
            }
        }
        else {
            val pers: Persona = Persona(
                binding.tvUsuario.text.toString(),
                binding.txtLevel.text.toString().toInt()
            )
            var codigo= Conexion.addPersona(this, pers)

            //la L es por ser un Long lo que trae codigo.
            if(codigo!=-1L) {
                Toast.makeText(this, "NUEVO RÉCORD", Toast.LENGTH_SHORT).show()
            }
            else{
                val builder = AlertDialog.Builder(this)

                with(builder)
                {
                    setTitle("EXISTE")
                    setMessage("Ya existe ese NOMBRE.")
                    setPositiveButton("Aceptar", DialogInterface.OnClickListener(positiveButtonClick))
                    show()
                }
            }
        }
    }

    fun modPersona() {
        if (binding.tvUsuario.text.toString().trim().isEmpty()){
            Toast.makeText(this, "Campos en blanco", Toast.LENGTH_SHORT).show()
        }
        else {
            var pers: Persona = Persona(
                binding.tvUsuario.text.toString(),
                binding.txtLevel.text.toString().toInt()
            )
            var cant = Conexion.modPersona(this, binding.tvUsuario.text.toString(), pers)
            if (cant == 1)
                Toast.makeText(this, "NUEVO RÉCORD", Toast.LENGTH_SHORT).show()
            else{
                val builder = AlertDialog.Builder(this)

                with(builder)
                {
                    setTitle("NO EXISTE")
                    setMessage("No existe un personaje con ese NOMBRE")
                    setPositiveButton("Aceptar", DialogInterface.OnClickListener(positiveButtonClick))
                    show()
                }
            }
        }
    }

    fun buscarPersona() {
        var p:Persona? = null
        p = Conexion.buscarPersona(this, binding.tvUsuario.text.toString())
        if (p!=null) {
            if (p.maxnivel < binding.txtLevel.text.toString().toInt()){
                modPersona()
            }else{
                Toast.makeText(this, "TE EQUIVOCASTE", Toast.LENGTH_SHORT).show()
            }
        } else {
            addPersona()
        }
    }
}