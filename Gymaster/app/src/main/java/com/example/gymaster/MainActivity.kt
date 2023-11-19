package com.example.gymaster

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.gymaster.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var firebaseauth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    val TAG = "ACSCO"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para la autenticación, de cualquier tipo.
        firebaseauth = FirebaseAuth.getInstance()
        //------------------------------ Autenticación con email y password ------------------------------------
        binding.eBRegistrar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setTitle(getString(R.string.registroUsuario))
            val dialogLayout = inflater.inflate(R.layout.alert_dialog_registro, null)
            val edDialogEmail = dialogLayout.findViewById<EditText>(R.id.edDialogEmail)
            val edDialogPass = dialogLayout.findViewById<EditText>(R.id.edDialogPass)
            val edDialogPass2 = dialogLayout.findViewById<EditText>(R.id.edDialogPass2)
            builder.setView(dialogLayout)

            builder.setNegativeButton("Cerrar") { dialogInterface, i ->
                dialogInterface.dismiss()
            }

            builder.setPositiveButton("Registrar") { dialogInterface, i ->
                val email = edDialogEmail.text.toString()
                val pass = edDialogPass.text.toString()
                val pass2 = edDialogPass2.text.toString()

                if (email.isNotEmpty() && pass.isNotEmpty() && pass == pass2) {
                    firebaseauth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            irHome(it.result?.user?.email?:"", Proveedor.BASIC)
                        } else {
                            showAlert("Error registrando al usuario.")
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this, "Conexión no establecida", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    showAlert("Rellene los campos correctamente y asegúrese de que las contraseñas coincidan.")
                }
            }
            builder.show()
        }

        binding.eBIniciarsesion.setOnClickListener {
            val email = binding.txtEdEmail.text.toString()
            val password = binding.textEdPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseauth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            irHome(task.result?.user?.email ?: "", Proveedor.BASIC)
                            binding.txtEdEmail.setText("")
                            binding.textEdPassword.setText("")
                            binding.txtEdEmail.clearFocus()
                            binding.textEdPassword.clearFocus()
                        } else {
                            showAlert()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Conexión no establecida", Toast.LENGTH_SHORT).show()
                    }
            } else {
                showAlert("Rellene todos los campos.")
            }
        }

        //------------------ Login Google -------------------
        //------------------------------- -Autenticación Google --------------------------------------------------
        //se hace un signOut por si había algún login antes.
        firebaseauth.signOut()
        //clearGooglePlayServicesCache()

        //esta variable me conecta con  google. y todo este bloque prepara la ventana de google que se destripa en el loginInGoogle
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)
        binding.eBIniciarsesiongoogle.setOnClickListener {
            loginEnGoogle()
        }
    }

    //******************************* Para el login con Google ******************************
    //--------
    private fun loginEnGoogle(){
        //este método es nuestro.
        val signInClient = googleSignInClient.signInIntent
        launcherVentanaGoogle.launch(signInClient)
        //milauncherVentanaGoogle.launch(signInClient)
    }


    //con este launcher, abro la ventana que me lleva a la validacion de Google.
    private val launcherVentanaGoogle =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        //si la ventana va bien, se accede a las propiedades que trae la propia ventana q llamamos y recogemos en result.
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            manejarResultados(task)
        }
    }

    //es como una muñeca rusa, vamos desgranando, de la ventana a task y de task a los datos concretos que me da google.
    private fun manejarResultados(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                actualizarUI(account)
            }
        }
        else {
            Toast.makeText(this,task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    //esta funcion actualiza o repinta la interfaz de usuario UI.
    private fun actualizarUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        //pido un token, y con ese token, si todo va bien obtengo la info.
        firebaseauth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                //hacer account. y ver otras propiedades interesantes.
                irHome(account.email.toString(),Proveedor.GOOGLE, account.displayName.toString())
            }
            else {
                Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }



    //************************************** Funciones auxiliares **************************************
    //*********************************************************************************
    private fun showAlert(msg:String = "Se ha producido un error autenticando al usuario"){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    //*********************************************************************************
    private fun irHome(email:String, provider:Proveedor, nombre:String = "Usuario"){
        Log.e(TAG,"Valores: ${email}, ${provider}, ${nombre}")
        val homeIntent = Intent(this, Home::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
            putExtra("nombre",nombre)
        }
        startActivity(homeIntent)
    }
}