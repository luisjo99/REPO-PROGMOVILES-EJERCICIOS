package com.example.gymaster

import Adaptadores.MiAdaptadorRecycler
import Auxiliar.Conexion
import Modelo.Almacen
import Modelo.Ejercicio
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gymaster.databinding.ActivityVentana1Binding
import java.util.Calendar

class Ventana1 : AppCompatActivity() {

    lateinit var binding: ActivityVentana1Binding
    private val channelId = "channelId"
    private val channelName = "notification"

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
    }
    lateinit var miRecyclerView : RecyclerView
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var contextoPrincipal: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVentana1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf(getString(R.string.banca), getString(R.string.sentadilla), getString(R.string.frontal), getString(R.string.pesoMuerto), getString(R.string.prensa), getString(R.string.remo))
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.edNombre.setAdapter(adapter)

        findViewById<AutoCompleteTextView>(R.id.edNombre).setAdapter(adapter)

        binding.toolbar1.title = "    GYMASTER"
        binding.toolbar1.subtitle =     getString(R.string.addEjercicios)
        binding.toolbar1.setLogo(R.drawable.ic_addejer)

        //aquí simplemente inflo la toolBaar, pero aún no hay opciones ni botón home.
        setSupportActionBar(binding.toolbar1)

        //en las siguientes líneas hago que aaprezca el botón de atrás (home) y genero su evento
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar1.setNavigationOnClickListener {
            listarEjercicios(binding.root)
            finish()
        }

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.edDate.setText(selectedDate)
            },
            // Establecer la fecha actual como predeterminada
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )

        binding.edDate.setOnClickListener {
            datePickerDialog.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEjercicio(view: View) {
        if (binding.edNombre.text.toString().trim().isEmpty() || binding.edMaxpeso.text.toString().trim().isEmpty()
            || binding.edDate.text.toString().trim().isEmpty()){
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("ERROR")
                setMessage("Campos en blanco")
                setPositiveButton("Aceptar", DialogInterface.OnClickListener(positiveButtonClick))
                show()
            }
        }
        else {
            var ejer: Ejercicio = Ejercicio(
                intent.getStringExtra("email").toString(),
                binding.edNombre.text.toString(),
                binding.edMaxpeso.text.toString().toDoubleOrNull() ?: 0.0,
                binding.edDate.text.toString(),
                binding.edNombre.text.toString().replace("\\s+".toRegex(), "").toLowerCase()
            )
            var codigo= Conexion.addEjercicio(this, ejer)

            //la L es por ser un Long lo que trae codigo.
            if(codigo!=-1L) {
                Toast.makeText(this, "Ejercicio insertado", Toast.LENGTH_SHORT).show()
                crearCanalNotificacion()

                // Crear un PendingIntent para la acción al pulsar la notificación
                val resultIntent = Intent(this, Home::class.java).apply {
                    putExtra("email", intent.getStringExtra("email").toString())
                    putExtra("provider", intent.getStringExtra("provider").toString())
                    putExtra("nombre", intent.getStringExtra("nombre").toString())
                }
                val resultPendingIntent = TaskStackBuilder.create(this).run {
                    addNextIntentWithParentStack(resultIntent)
                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                }

                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notificationId = 1

                val builder = NotificationCompat.Builder(this, channelId).also {
                    it.setSmallIcon(R.drawable.ic_addejer)
                    it.setContentTitle("¡Gracias por usar nuestra app!")
                    it.setContentText("Ya está añadido su nuevo récord")
                    it.setContentIntent(resultPendingIntent)
                    it.setAutoCancel(true)
                }.build()

                notificationManager.notify(notificationId, builder)

                //listarEjercicios(view)
                binding.edNombre.setText("")
                binding.edMaxpeso.setText("")
                binding.edDate.setText("")
                binding.edNombre.requestFocus()
            }
            else{
                val builder = AlertDialog.Builder(this)

                with(builder)
                {
                    setTitle("EXISTE")
                    setMessage("Ya existe ese NOMBRE. Ejercicio NO insertado")
                    setPositiveButton("Aceptar", DialogInterface.OnClickListener(positiveButtonClick))
                    show()
                }
            }
        }
    }

    fun delEjercicio(view: View) {
        var cant = Conexion.delEjercicio(this, binding.edNombre.text.toString(), intent.getStringExtra("email").toString())
        if (cant == 1) {
            Toast.makeText(this, "Se borró el ejercicio con ese NOMBRE", Toast.LENGTH_SHORT).show()
            //listarEjercicios(view)
            binding.edNombre.setText("")
            binding.edDate.setText("")
            binding.edMaxpeso.setText("")
        }
        else{
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("NO EXISTE")
                setMessage("No existe un ejercicio con ese NOMBRE")
                setPositiveButton("Aceptar", DialogInterface.OnClickListener(positiveButtonClick))
                show()
            }
        }
    }

    fun modEjercicio(view: View) {
        if (binding.edNombre.text.toString().trim().isEmpty()|| binding.edMaxpeso.text.toString().trim().isEmpty()
            || binding.edDate.text.toString().trim().isEmpty()){
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("ERROR")
                setMessage("Campos en blanco")
                setPositiveButton("Aceptar", DialogInterface.OnClickListener(positiveButtonClick))
                show()
            }
        }
        else {
            var ejer: Ejercicio = Ejercicio(
                intent.getStringExtra("email").toString(),
                binding.edNombre.text.toString(),
                binding.edMaxpeso.text.toString().toDoubleOrNull() ?: 0.0,
                binding.edDate.text.toString(),
                binding.edNombre.text.toString().replace("\\s+".toRegex(), "").toLowerCase()
            )
            var cant = Conexion.modEjercicio(this, binding.edNombre.text.toString(), ejer)
            if (cant == 1) {
                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show()
                binding.edNombre.setText("")
                binding.edMaxpeso.setText("")
                binding.edDate.setText("")
                binding.edNombre.requestFocus()
            }
            else{
                val builder = AlertDialog.Builder(this)

                with(builder)
                {
                    setTitle("NO EXISTE")
                    setMessage("No existe un ejercicio con ese NOMBRE")
                    setPositiveButton("Aceptar", DialogInterface.OnClickListener(positiveButtonClick))
                    show()
                }
            }
        }
        //listarEjercicios(view)
    }

    fun buscarEjercicio(view: View) {
        var e:Ejercicio? = null
        e = Conexion.buscarEjercicio(this, binding.edNombre.text.toString(), intent.getStringExtra("email").toString())
        if (e!=null) {
            binding.edMaxpeso.setText(e.weight.toString())
            binding.edDate.setText(e.date)
        } else {
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("NO EXISTE")
                setMessage("No existe un ejercicio con ese NOMBRE")
                setPositiveButton("Aceptar", DialogInterface.OnClickListener(positiveButtonClick))
                show()
            }
        }
    }

    fun listarEjercicios(view: View) {

        val userEmail = intent.getStringExtra("email").toString()
        Almacen.ejercicios = Conexion.obtenerEjercicios(this, userEmail)
        // Actualiza los datos en tu lista

        // Crea un nuevo adaptador con los datos actualizados
        var miAdapter = MiAdaptadorRecycler(Almacen.ejercicios, this)

        // Establece el nuevo adaptador en tu RecyclerView
        miRecyclerView.adapter = miAdapter

        // Notifica al adaptador que los datos han cambiado
        miAdapter.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun crearCanalNotificacion() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}