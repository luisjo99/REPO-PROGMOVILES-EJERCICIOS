package com.example.recyclerjuegolss

import Auxiliar.Conexion
import Modelo.Personaje
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity3 : AppCompatActivity() {

    lateinit var edNombre: EditText
    lateinit var edCarril: EditText
    lateinit var edArquetipo: EditText
    lateinit var edImagen: EditText
    lateinit var botonAdd: Button
    lateinit var botonBuscar: Button
    lateinit var botonBorrar: Button
    lateinit var botonEditar: Button
    lateinit var txtListdo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        edNombre.requestFocus()
        edNombre = findViewById(R.id.edNombre)
        edCarril = findViewById(R.id.edCarril)
        edArquetipo = findViewById(R.id.edArquetipo)
        edImagen = findViewById(R.id.edImagen)
        botonAdd = findViewById(R.id.btnAdd)
        botonBuscar = findViewById(R.id.btnBuscar)
        botonBorrar = findViewById(R.id.btnBorrar)
        botonEditar = findViewById(R.id.btnEditar)
        txtListdo = findViewById(R.id.txtListado)
    }

    fun addPersonaje(view: View) {
        if (edNombre.text.toString().trim().isEmpty() || edCarril.text.toString().trim().isEmpty()
            || edArquetipo.text.toString().trim().isEmpty() || edImagen.text.toString().trim().isEmpty()){
            Toast.makeText(this, "Campos en blanco", Toast.LENGTH_SHORT).show()
        }
        else {
            var pers: Personaje = Personaje(
                edNombre.getText().toString(),
                edCarril.getText().toString(),
                edArquetipo.getText().toString(),
                edImagen.getText().toString()
            )
            var codigo= Conexion.addPersonaje(this, pers)
            edNombre.setText("")
            edCarril.setText("")
            edArquetipo.setText("")
            edImagen.setText("")
            edNombre.requestFocus()
            //la L es por ser un Long lo que trae codigo.
            if(codigo!=-1L) {
                Toast.makeText(this, "Personaje insertado", Toast.LENGTH_SHORT).show()
                listarPersonajes(view)
            }
            else
                Toast.makeText(this, "Ya existe ese NOMBRE. Personaje NO insertado", Toast.LENGTH_SHORT).show()
        }
    }

    fun delPersonaje(view: View) {
        var cant = Conexion.delPersonaje(this, edNombre.text.toString())
        edNombre.setText("")
        edCarril.setText("")
        edArquetipo.setText("")
        edImagen.setText("")
        if (cant == 1) {
            Toast.makeText(this, "Se borró el personaje con ese NOMBRE", Toast.LENGTH_SHORT).show()
            listarPersonajes(view)
        }
        else
            Toast.makeText(this, "No existe un personaje con ese NOMBRE", Toast.LENGTH_SHORT).show()
    }

    fun modPersonaje(view: View) {
        if (edNombre.text.toString().trim().isEmpty()|| edCarril.text.toString().trim().isEmpty()
            || edArquetipo.text.toString().trim().isEmpty() || edImagen.text.toString().trim().isEmpty()){
            Toast.makeText(this, "Campos en blanco", Toast.LENGTH_SHORT).show()
        }
        else {
            var pers: Personaje = Personaje(
                edNombre.getText().toString(),
                edCarril.getText().toString(),
                edArquetipo.getText().toString(),
                edImagen.getText().toString()
            )
            var cant = Conexion.modPersonaje(this, edNombre.text.toString(), pers)
            if (cant == 1)
                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe un personaje con ese NOMBRE", Toast.LENGTH_SHORT).show()
        }
        listarPersonajes(view)
    }

    fun buscarPersonaje(view: View) {
        var p:Personaje? = null
        p = Conexion.buscarPersonaje(this, edNombre.text.toString())
        if (p!=null) {
            edCarril.setText(p.carril)
            edArquetipo.setText(p.arquetipo)
            edImagen.setText(p.imagen)
        } else {
            Toast.makeText(this, "No existe un personaje con ese NOMBRE", Toast.LENGTH_SHORT).show()
        }

    }

    fun listarPersonajes(view: View) {
        var listado:ArrayList<Personaje> = Conexion.obtenerPersonajes(this)

        txtListdo.setText("")

        if (listado.size==0) {
            Toast.makeText(this, "No existen datos en la tabla", Toast.LENGTH_SHORT).show()
        }
        else {
            for(p in listado){
                var cadena = p.nombre + ", " + p.carril + ", " + p.arquetipo + ", " + p.imagen + "\r\n"
                txtListdo.append(cadena)
            }
        }
    }
}