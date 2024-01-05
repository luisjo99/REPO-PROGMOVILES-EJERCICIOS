package com.example.gymaster

import Modelo.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.gymaster.databinding.ActivityVentana2Binding
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.Random

class Ventana2 : AppCompatActivity() {

    var miArray: ArrayList<User> = ArrayList()
    lateinit var binding: ActivityVentana2Binding
    private lateinit var firebaseauth: FirebaseAuth
    val TAG = "ACSCO"
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVentana2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Para la autenticación, de cualquier tipo.
        firebaseauth = FirebaseAuth.getInstance()

        binding.toolbar1.title = "    GYMASTER"
        binding.toolbar1.subtitle = getString(R.string.ajustesUsuario)
        binding.toolbar1.setLogo(R.drawable.ic_settings)

        // aquí simplemente inflo la toolBaar, pero aún no hay opciones ni botón home.
        setSupportActionBar(binding.toolbar1)

        // en las siguientes líneas hago que aparezca el botón de atrás (home) y genero su evento
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar1.setNavigationOnClickListener {
            finish()
        }

        // Recuperamos los datos del login.
        binding.txtEmail.text = intent.getStringExtra("email").toString()
        binding.txtProveedor.text = intent.getStringExtra("provider").toString()
        binding.txtNombre.text = intent.getStringExtra("nombre").toString()

        binding.btGuardar.setOnClickListener {
            // Obtener datos de los campos de entrada
            val nombre = binding.edNombre.text.toString()
            val edadStr = binding.edEdad.text.toString()

            if (nombre.isNotEmpty() && edadStr.isNotEmpty()) {
                // Crear el mapa de usuario
                val user = hashMapOf(
                    "provider" to binding.txtProveedor.text,
                    "email" to binding.txtEmail.text.toString(),
                    "name" to nombre,
                    "age" to edadStr,
                    "roles" to arrayListOf("premium"),
                    "timestamp" to FieldValue.serverTimestamp()
                )

                // Si no existe el documento, lo crea; si existe, lo reemplaza.
                db.collection("users")
                    .document(user["email"].toString())
                    .set(user)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Introduce el nombre y la edad", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btRecuperar.setOnClickListener {
            var roles: ArrayList<String>

            db.collection("users")
                .document(binding.txtEmail.text.toString())
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    binding.edNombre.setText(documentSnapshot.get("name") as String?)
                    binding.edEdad.setText(documentSnapshot.get("age") as String?)
                    roles = documentSnapshot.get("roles") as ArrayList<String>? ?: arrayListOf("normal")

                    if (roles.isNotEmpty()) {
                        // El usuario tiene el rol "premium"
                        binding.txtRoles.text = roles.toString()
                    } else {
                        // El usuario no tiene el rol "premium"
                        Log.e(TAG, "Sin rol premium")
                        binding.txtRoles.text = "SIN ROLES"
                    }

                    Toast.makeText(this, "Recuperado", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
                }
        }

        binding.btnActualizarPass.setOnClickListener {
            val antiguaContraseña = binding.edPassAntigua.text.toString()
            val nuevaContraseña = binding.edPassNueva.text.toString()

            if (nuevaContraseña.isNotEmpty()) {
                // Obtén el usuario actual de Firebase
                val user = FirebaseAuth.getInstance().currentUser

                // Verifica la contraseña antigua antes de intentar la actualización
                val credential = EmailAuthProvider.getCredential(user?.email.toString(), antiguaContraseña)
                user?.reauthenticate(credential)
                    ?.addOnCompleteListener { reauthTask ->
                        if (reauthTask.isSuccessful) {
                            // La autenticación ha sido exitosa, ahora actualiza la contraseña
                            user.updatePassword(nuevaContraseña)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        Log.d(TAG, "User password updated.")
                                        Toast.makeText(this, "Contraseña actualizada con éxito", Toast.LENGTH_SHORT).show()
                                        binding.edPassAntigua.setText("")
                                        binding.edPassNueva.setText("")
                                    } else {
                                        Log.e(TAG, "Failed to update user password", updateTask.exception)
                                        Toast.makeText(this, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Log.e(TAG, "Reauthentication failed", reauthTask.exception)
                            Toast.makeText(this, "Contraseña antigua incorrecta", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Introduce la nueva contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btEliminar.setOnClickListener {
            //Buscamos antes si existe un campo con ese email en un documento.
            val id = db.collection("users")
                .whereEqualTo("email",binding.txtEmail.text.toString())
                .get()
                .addOnSuccessListener {result ->
                    //En result, vienen los que cumplen la condición (si no pongo nada es it)
                    //Con esto borramos el primero.
                    //db.collection("users").document(result.elementAt(0).id).delete().toString()
                    //Con esto borramos todos. No olvidar que id aquí no es una Primarykey, puede repetirse.
                    for (document in result) {
                        db.collection("users")
                            .document(document.id)
                            .delete().toString() //lo importante aquí es el delete. el toString es pq además devuelve un mensaje con lo sucedido.
                    }

                    Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "No se ha encontrado el documento a eliminar", Toast.LENGTH_SHORT).show()
                }

        }

        //https://cloud.google.com/firestore/docs/query-data/queries?hl=es-419#kotlin+ktxandroid_3

    }//fin onCreate
//------------------------------------------------------------------------------------------
    /**
     * Este método es una función suspend que esperará a que la consulta se realiza. Será llamada
     * en un scope (entorno) de corrutinas. Hilos (ver onCreate, runBlocking).
     */
    suspend fun getDataFromFireStore()  : QuerySnapshot? {
        return try{
            val data = db.collection("users")
                //.whereEqualTo("name", "Lagertha")
                .whereGreaterThanOrEqualTo("age",18)  //https://firebase.google.com/docs/firestore/query-data/order-limit-data?hl=es-419
                .orderBy("age", Query.Direction.DESCENDING)
                //.limit(4) //Limita la cantidad de elementos mostrados.
                .get()
                .await()
            data
        }catch (e : Exception){
            null
        }
    }
    //*****************************************************************************************************************
    //********************** Métodos de creación/modificación/borrado de datos ****************************************
    //*****************************************************************************************************************

    /**
     * Función que recuperará los datos obtenidos del método: getDataFromFireStore().
     * Llamada también desde el entorno de corrutinas: (ver onCreate, runBlocking).
     */
    private fun obtenerDatos(datos: QuerySnapshot?) {
        for(dc: DocumentChange in datos?.documentChanges!!){
            if (dc.type == DocumentChange.Type.ADDED){
                //miAr.add(dc.document.toObject(User::class.java))
                var roles : ArrayList<Int>

                if (dc.document.get("roles") != null){
                    roles = dc.document.get("roles") as ArrayList<Int>
                }
                else {
                    roles = arrayListOf()
                }
                var al = User(
                    dc.document.get("age").toString(),
                    dc.document.get("first").toString(),
                    dc.document.get("last").toString(),
                    roles
                )
                //Log.e(TAG, al.toString())
                miArray.add(al)
            }
        }
    }
}