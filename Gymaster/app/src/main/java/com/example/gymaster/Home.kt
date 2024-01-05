package com.example.gymaster

import Adaptadores.MiAdaptadorRecycler
import Auxiliar.Conexion
import Modelo.Almacen
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymaster.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import org.checkerframework.framework.qual.InvisibleQualifier

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseauth : FirebaseAuth
    val TAG = "ACSCO"
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
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarPrincipal.title = "    GYMASTER"
        binding.toolbarPrincipal.subtitle = intent.getStringExtra("email").toString()
        binding.toolbarPrincipal.setLogo(R.drawable.ic_usuario)

        //aquí simplemente inflo la toolBaar, pero aún no hay opciones ni botón home.
        setSupportActionBar(binding.toolbarPrincipal)

        //en las siguientes líneas hago que aaprezca el botón de atrás (home) y genero su evento
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.toolbarPrincipal.setNavigationOnClickListener {
            Toast.makeText(this,"Atrás", Toast.LENGTH_SHORT).show()
        }

        //Para la autenticación, de cualquier tipo.
        firebaseauth = FirebaseAuth.getInstance()


        binding.btCerrarSesion.setOnClickListener {
            Log.e(TAG, firebaseauth.currentUser.toString())
            // Olvidar al usuario, limpiando cualquier referencia persistente
            //comprobadlo en Firebase, como ha desaparecido.
//            firebaseauth.currentUser?.delete()?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    firebaseauth.signOut()
//                    Log.e(TAG,"Cerrada sesión completamente")
//                } else {
//                    Log.e(TAG,"Hubo algún error al cerrar la sesión")
//                }
//            }
            firebaseauth.signOut()

            val signInClient = Identity.getSignInClient(this)
            signInClient.signOut()
            Log.e(TAG,"Cerrada sesión completamente")
            finish()
        }

        val userEmail = intent.getStringExtra("email").toString()
        Almacen.ejercicios = Conexion.obtenerEjercicios(this, userEmail)

        miRecyclerView = binding.listaEjerciciosRecycler as RecyclerView
        miRecyclerView.setHasFixedSize(true)//hace que se ajuste a lo que has diseñado
        miRecyclerView.layoutManager = LinearLayoutManager(this)//se dice el tipo de Layout, dejampos este.
        //esta es la clave. Creo un objeto de tipo Mi Adaptador y le paso la lista que he creado prevaimente más arriba.
        //aquí, es donde inflará y pintará cada CardView.
        var miAdapter = MiAdaptadorRecycler(Almacen.ejercicios, this)
        //aquí es donde hace la "magia", al pasarle a mi Recicler View, el adaptador creado.
        miRecyclerView.adapter = miAdapter

        Ventana1.contextoPrincipal = this

        if (Almacen.ejercicios.size > 0) {
            // Si la lista tiene elementos, hacer invisible el TextView
            binding.txtRecycler.visibility = View.INVISIBLE
        } else {
            // Si la lista está vacía, hacer visible el TextView
            binding.txtRecycler.visibility = View.VISIBLE
        }
    }
    //************************* Funciones auxiliares para los menú de puntos *****************************
    //la primera infla el menú que previamente hemos creado como resource, la segunda carga las opciones.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mnOp1 -> {
                irAVentana1()
            }
            R.id.mnOp2 -> {
                irAVentana2()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    /*private fun irAVentanaOpcion1() {

        var miIntent: Intent = Intent(this, VentanaOpcion1::class.java)
        startActivity(miIntent)
    }*/
    //version lambda de la funcion. Unit equivale a void de Java.
    private val irAVentana1: () -> Unit = {
        val miIntent = Intent(this, Ventana1::class.java).apply {
            putExtra("email", intent.getStringExtra("email").toString())
            putExtra("provider", intent.getStringExtra("provider").toString())
            putExtra("nombre",intent.getStringExtra("nombre").toString())
        }
        startActivity(miIntent)
    }

    private val irAVentana2: () -> Unit = {
        val miIntent = Intent(this, Ventana2::class.java).apply {
            putExtra("email", intent.getStringExtra("email").toString())
            putExtra("provider", intent.getStringExtra("provider").toString())
            putExtra("nombre",intent.getStringExtra("nombre").toString())
        }
        startActivity(miIntent)
    }
}