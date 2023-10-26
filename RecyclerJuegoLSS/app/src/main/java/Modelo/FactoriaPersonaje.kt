package Modelo

import kotlin.random.Random

object FactoriaPersonaje {
    fun generaPersonaje(): Personaje {
        val personajes = listOf(
            Personaje("Khazix", "Jungla", "Asesino", "khazix"),
            Personaje("Viego", "Jungla", "Luchador", "viego"),
            Personaje("Fizz", "Medio", "Asesino", "fizz"),
            Personaje("Yone", "Medio", "Asesino", "yone"),
            Personaje("Yasuo", "Medio", "Luchador", "yasuo")
        )
        val random = Random.nextInt(personajes.size)
        return personajes[random]
    }
}