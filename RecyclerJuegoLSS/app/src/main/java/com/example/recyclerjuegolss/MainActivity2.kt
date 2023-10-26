package com.example.recyclerjuegolss

import Adaptadores.MiAdaptadorRecycler
import Modelo.Personaje
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.recyclerjuegolss.databinding.ActivityMain2Binding
import com.example.recyclerjuegolss.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var p = intent.getSerializableExtra("obj") as Personaje
        val imagenNombre = p.imagen
        val imgId = resources.getIdentifier(imagenNombre, "drawable", packageName)

        if (imgId != 0) {
            binding.txtName.text = p.nombre
            binding.imgImagen2.setImageResource(imgId)
        }

        if (p.nombre.equals("Khazix")){
            binding.txtDescripcion.setText("Kha'Zix salta hacia una zona e inflige daño físico al aterrizar. " +
                    "Si decide evolucionar Alas, el alcance de Salto aumenta en 200. Además, al matar o " +
                    "ayudar a matar a un campeón, el enfriamiento de Salto se restablece.")
        }

        if (p.nombre.equals("Yone")){
            binding.txtDescripcion.setText("Yone entra en su forma espiritual, lo que le otorga velocidad de " +
                    "movimiento y hace que abandone su cuerpo. Cuando acaba el tiempo de la forma espiritual " +
                    "de Yone, vuelve a su cuerpo e inflige un porcentaje de todo el daño infligido durante " +
                    "su forma espiritual.")
        }

        if (p.nombre.equals("Yasuo")){
            binding.txtDescripcion.setText("Yasuo, un jonio de profunda determinación, es un ágil espadachín " +
                    "que empuña al propio viento contra sus enemigos.")
        }

        if (p.nombre.equals("Viego")){
            binding.txtDescripcion.setText("Viego se deshace del cuerpo que haya poseído y se teleporta hacia " +
                    "delante, atacando al campeón enemigo con el menor porcentaje de vida que se encuentre " +
                    "a su alcance e infligiéndole daño adicional en función de la vida que le falte. Empuja " +
                    "a los demás enemigos que se encuentren a su alcance.")
        }

        if (p.nombre.equals("Fizz")){
            binding.txtDescripcion.setText("Fizz es un yordle prehistórico, de al menos más de 10000 años, " +
                    "que se remonta a una época en la que no había civilizaciones masivas en la tierra. " +
                    "Pudo sobrevivir durante milenios debido a la hibernación y al ser un Yordle, una raza " +
                    "mágica que no envejece.")
        }

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}