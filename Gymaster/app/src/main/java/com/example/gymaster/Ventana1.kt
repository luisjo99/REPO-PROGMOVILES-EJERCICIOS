package com.example.gymaster

import Adaptadores.MiAdaptadorRecycler
import Auxiliar.Conexion
import Modelo.Almacen
import Modelo.Ejercicio
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.gymaster.databinding.ActivityVentana1Binding
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class Ventana1 : AppCompatActivity() {

    lateinit var binding: ActivityVentana1Binding

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Has pulsado sí", Toast.LENGTH_SHORT).show()
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

        val items = listOf("Press Banca", "Sentadilla", "Press Frontal", "Peso Muerto")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.edNombre.setAdapter(adapter)


        findViewById<AutoCompleteTextView>(R.id.edNombre).setAdapter(adapter)
        binding.toolbar1.title = "    GYMASTER"
        binding.toolbar1.subtitle = "     AÑADIR EJERCICIOS"
        binding.toolbar1.setLogo(R.drawable.ic_addejer)

        //aquí simplemente inflo la toolBaar, pero aún no hay opciones ni botón home.
        setSupportActionBar(binding.toolbar1)

        //en las siguientes líneas hago que aaprezca el botón de atrás (home) y genero su evento
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar1.setNavigationOnClickListener {
            listarEjercicios(binding.root)
            finish()
        }
    }

    fun addEjercicio(view: View) {
        if (binding.edNombre.text.toString().trim().isEmpty() || binding.edMaxpeso.text.toString().trim().isEmpty()
            || binding.edDate.text.toString().trim().isEmpty() || binding.edImagen.text.toString().trim().isEmpty()){
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
                binding.edNombre.text.toString(),
                binding.edMaxpeso.text.toString().toDoubleOrNull() ?: 0.0,
                binding.edDate.text.toString(),
                binding.edImagen.text.toString()
            )
            var codigo= Conexion.addEjercicio(this, ejer)
            binding.edNombre.setText("")
            binding.edMaxpeso.setText("")
            binding.edDate.setText("")
            binding.edImagen.setText("")
            binding.edNombre.requestFocus()
            //la L es por ser un Long lo que trae codigo.
            if(codigo!=-1L) {
                Toast.makeText(this, "Personaje insertado", Toast.LENGTH_SHORT).show()
                //listarEjercicios(view)
            }
            else{
                val builder = AlertDialog.Builder(this)

                with(builder)
                {
                    setTitle("EXISTE")
                    setMessage("Ya existe ese NOMBRE. Personaje NO insertado")
                    setPositiveButton("Aceptar", DialogInterface.OnClickListener(positiveButtonClick))
                    show()
                }
            }
        }
    }

    fun delEjercicio(view: View) {
        var cant = Conexion.delEjercicio(this, binding.edNombre.text.toString())
        binding.edNombre.setText("")
        binding.edDate.setText("")
        binding.edMaxpeso.setText("")
        binding.edImagen.setText("")
        if (cant == 1) {
            Toast.makeText(this, "Se borró el personaje con ese NOMBRE", Toast.LENGTH_SHORT).show()
            //listarEjercicios(view)
        }
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

    fun modEjercicio(view: View) {
        if (binding.edNombre.text.toString().trim().isEmpty()|| binding.edMaxpeso.text.toString().trim().isEmpty()
            || binding.edDate.text.toString().trim().isEmpty() || binding.edImagen.text.toString().trim().isEmpty()){
            Toast.makeText(this, "Campos en blanco", Toast.LENGTH_SHORT).show()
        }
        else {
            var ejer: Ejercicio = Ejercicio(
                binding.edNombre.getText().toString(),
                binding.edMaxpeso.text.toString().toDoubleOrNull() ?: 0.0,
                binding.edDate.getText().toString(),
                binding.edImagen.getText().toString()
            )
            var cant = Conexion.modEjercicio(this, binding.edNombre.text.toString(), ejer)
            if (cant == 1)
                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show()
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
        //listarEjercicios(view)
    }

    fun buscarEjercicio(view: View) {
        var e:Ejercicio? = null
        e = Conexion.buscarEjercicio(this, binding.edNombre.text.toString())
        if (e!=null) {
            binding.edMaxpeso.setText(e.weight.toString())
            binding.edDate.setText(e.date)
            binding.edImagen.setText(e.imagen)
        } else {
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

    fun listarEjercicios(view: View) {

        // Actualiza los datos en tu lista
        Almacen.ejercicios = Conexion.obtenerEjercicios(this)

        // Crea un nuevo adaptador con los datos actualizados
        var miAdapter = MiAdaptadorRecycler(Almacen.ejercicios, this)

        // Establece el nuevo adaptador en tu RecyclerView
        miRecyclerView.adapter = miAdapter

        // Notifica al adaptador que los datos han cambiado
        miAdapter.notifyDataSetChanged()
    }
}