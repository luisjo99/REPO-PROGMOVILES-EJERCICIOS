package com.example.encuesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.encuesta.databinding.ActivityMainBinding
import modelo.AlmacenPersonas
import modelo.Persona
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sbSeekbar.setOnSeekBarChangeListener(object:
            SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                var progreso = binding.sbSeekbar.progress
                binding.txtHoras.setText(progreso.toString())
            }
            override fun onStartTrackingTouch(seek: SeekBar) {
            }
            override fun onStopTrackingTouch(seek: SeekBar) {
                var progreso = binding.sbSeekbar.progress
                binding.txtHoras.setText(progreso.toString())
            }
        })

        binding.swAnonimo.setOnClickListener{
            if (binding.swAnonimo.isChecked){
                binding.txtNombre.visibility=View.INVISIBLE
                binding.edNombre.visibility=View.INVISIBLE
            } else{
                binding.txtNombre.visibility=View.VISIBLE
                binding.edNombre.visibility=View.VISIBLE
            }
        }

        binding.btnValidar.setOnClickListener {
            var nombre: String=""
            var horas = binding.txtHoras.text.toString()
            var sisOpe: String = ""
            var especialidades = mutableListOf<String>()

            if (binding.swAnonimo.isChecked){
                nombre="Anónimo"
            }else {
                nombre = binding.edNombre.text.toString()
            }

            if (binding.rbMac.isChecked) {
                sisOpe = "Mac"
            } else if (binding.rbWindows.isChecked) {
                sisOpe = "Windows"
            } else if (binding.rbLinux.isChecked) {
                sisOpe = "Linux"
            }

            if (binding.cbDam.isChecked) {
                especialidades.add("DAM")
            }
            if (binding.cbAsir.isChecked) {
                especialidades.add("ASIR")
            }
            if (binding.cbDaw.isChecked) {
                especialidades.add("DAW")
            }

            if (nombre.isNullOrEmpty() || sisOpe.isEmpty() || horas.isEmpty() || especialidades.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos antes de validar.", Toast.LENGTH_SHORT).show()
            } else {
                var persona: Persona = Persona(nombre, sisOpe, especialidades.joinToString(", "), horas)
                AlmacenPersonas.aniadirPersona(persona)
                Toast.makeText(this, "Encuesta guardada correctamente.", Toast.LENGTH_SHORT).show()
                reiniciar()
            }
        }

        binding.btnReiniciar.setOnClickListener {
            reiniciar()
            AlmacenPersonas.personas.clear()
        }

        binding.btnCuantas.setOnClickListener {
            var encuestas = AlmacenPersonas.personas.size
            Toast.makeText(this, "El total de encuestas realizadas es $encuestas",Toast.LENGTH_SHORT).show()
        }

        binding.btnResumen.setOnClickListener {
            var cadena: String =""
            var i:Int=1
            for (p in AlmacenPersonas.personas){
                cadena+="Encuesta"+i+"(\""+p.nombre+"\", \""+p.sisOpe+"\", [\""+p.especialidad+"\"], "+p.horasEstudio+")\n"
                i++
                binding.edmEncuestas.setText(cadena)
            }
        }
    }
    fun reiniciar(){
        var progreso = 0
        binding.swAnonimo.isChecked=false
        binding.edmEncuestas.setText("")
        binding.edNombre.setText("")
        binding.rgRadioGroup.clearCheck()
        binding.cbDam.isChecked = false
        binding.cbAsir.isChecked = false
        binding.cbDaw.isChecked = false
        binding.sbSeekbar.progress=0
        binding.txtHoras.setText(progreso.toString())
        binding.txtNombre.visibility=View.VISIBLE
        binding.edNombre.visibility=View.VISIBLE
    }
}
