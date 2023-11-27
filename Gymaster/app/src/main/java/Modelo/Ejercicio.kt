package Modelo

import java.io.Serializable

data class Ejercicio(var email:String, var nombre:String, var weight: Double, var date:String, var imagen: String) : Serializable
