package com.example.gymaster

import Modelo.Ejercicio
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymaster.databinding.ActivityRecyclerDetalleBinding

class RecyclerDetalle : AppCompatActivity() {

    lateinit var binding: ActivityRecyclerDetalleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var e = intent.getSerializableExtra("obj") as Ejercicio
        val imagenNombre = e.imagen
        val imgId = resources.getIdentifier(imagenNombre, "drawable", packageName)

        if (imgId != 0) {
            binding.txtName.text = e.nombre
            binding.imgImagen2.setImageResource(imgId)
        }

        if (e.nombre.equals("Press Banca")){
            binding.txtDescripcion.setText("El press de banca es un ejercicio de levantamiento de pesas " +
                    "realizado acostado en un banco horizontal. Se centra en desarrollar la fuerza y " +
                    "masa muscular del pecho, tríceps y hombros. Es esencial en entrenamientos de fuerza " +
                    "y a menudo adaptado para objetivos específicos.")
        }

        if (e.nombre.equals("Sentadilla")){
            binding.txtDescripcion.setText("\n" +
                    "La sentadilla es un ejercicio fundamental que involucra flexionar las rodillas y " +
                    "caderas, descendiendo el cuerpo hacia abajo y luego volviendo a la posición inicial. " +
                    "Este movimiento trabaja eficazmente los músculos de las piernas, glúteos y la zona " +
                    "central, contribuyendo al desarrollo de fuerza y estabilidad.")
        }

        if (e.nombre.equals("Press Frontal")){
            binding.txtDescripcion.setText("\n" +
                    "El press frontal es un ejercicio de levantamiento de pesas que se realiza levantando " +
                    "una barra desde la parte frontal del cuerpo hacia arriba, por encima de la cabeza. " +
                    "Este movimiento fortalece los hombros y los músculos superiores del tronco, mejorando " +
                    "la fuerza y la estabilidad en la parte superior del cuerpo. ")
        }

        if (e.nombre.equals("Peso Muerto")){
            binding.txtDescripcion.setText("El peso muerto es un ejercicio fundamental de levantamiento de " +
                    "pesas que involucra levantar una barra cargada desde el suelo hasta la posición de pie. " +
                    "Este movimiento trabaja diversos grupos musculares, como la espalda baja, los glúteos, " +
                    "los isquiotibiales y los músculos de la cadena posterior.")
        }

        if (e.nombre.equals("Prensa")){
            binding.txtDescripcion.setText("La prensa es un ejercicio de entrenamiento de piernas que implica " +
                    "empujar una carga hacia arriba con las piernas. Este movimiento se realiza generalmente " +
                    "en una máquina de prensa, donde el usuario se sienta y empuja una plataforma hacia arriba " +
                    "con los pies. La prensa trabaja principalmente los músculos de las piernas, incluyendo " +
                    "cuádriceps, isquiotibiales y glúteos.")
        }

        if (e.nombre.equals("Remo en barra")){
            binding.txtDescripcion.setText("El remo en barra es un ejercicio de levantamiento de pesas que se " +
                    "realiza al jalar una barra hacia el torso. Este movimiento, a menudo realizado con la " +
                    "espalda recta y el torso inclinado hacia adelante, se centra en el desarrollo de los músculos " +
                    "de la espalda, incluyendo el trapecio, el dorsal ancho y los deltoides posteriores. ")
        }

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}