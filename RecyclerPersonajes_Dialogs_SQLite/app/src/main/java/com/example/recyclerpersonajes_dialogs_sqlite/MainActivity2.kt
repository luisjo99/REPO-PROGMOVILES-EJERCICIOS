package com.example.recyclerpersonajes_dialogs_sqlite

import Adaptadores.MiAdaptadorRecycler
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerpersonajes_dialogs_sqlite.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    lateinit var binding: ActivityMain2Binding

    lateinit var miRecyclerView : RecyclerView
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var contextoPrincipal: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        Almacen.personajes = FactoriaListaPersonaje.generaLista(12)
        Log.e("ACSCO", Almacen.personajes.toString())

        miRecyclerView = binding.listaPersonajesRecycler as RecyclerView
        miRecyclerView.setHasFixedSize(true)//hace que se ajuste a lo que has diseñado
        miRecyclerView.layoutManager = LinearLayoutManager(this)//se dice el tipo de Layout, dejampos este.
        //esta es la clave. Creo un objeto de tipo Mi Adaptador y le paso la lista que he creado prevaimente más arriba.
        //aquí, es donde inflará y pintará cada CardView.
        var miAdapter = MiAdaptadorRecycler(Almacen.personajes, this)
        //aquí es donde hace la "magia", al pasarle a mi Recicler View, el adaptador creado.
        miRecyclerView.adapter = miAdapter

        binding.btnDetalle.setOnClickListener {
            if (MiAdaptadorRecycler.seleccionado >= 0) {
                val pe = Almacen.personajes.get(MiAdaptadorRecycler.seleccionado)
                Log.e("ACSCO",pe.toString())
                var inte : Intent = Intent(contextoPrincipal, MainActivity2::class.java)
                inte.putExtra("obj", Almacen.personajes.get(MiAdaptadorRecycler.seleccionado))
                ContextCompat.startActivity(contextoPrincipal, inte, null)
            }
            else {
                Toast.makeText(this,"Selecciona algo previamente", Toast.LENGTH_SHORT).show()
            }
        }
        contextoPrincipal = this
    }
}