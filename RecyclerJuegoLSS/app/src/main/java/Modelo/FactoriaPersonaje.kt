package Modelo

import android.R

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat


object FactoriaPersonaje {
    fun generaPersonaje() : Personaje {
        var nombres = listOf<String>("Yasuo", "Yone", "Fizz", "Khazix", "Viego")
        var razas = listOf<String>("Orco", "Hombre", "Hobbit", "Elfo")
        var imagenes = listOf<String>()

        var yasuo = Personaje("Yasuo", "Mid", "Luchador", ContextCompat.getDrawable(this, R.drawable.yasuo))

        return yasuo
    }
}