package Adaptadores

import Modelo.Almacen
import Modelo.Personaje
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerjuegolss.MainActivity
import com.example.recyclerjuegolss.MainActivity2
import com.example.recyclerjuegolss.R

class MiAdaptadorRecycler (var personajes : ArrayList<Personaje>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>(){

    companion object {
        var seleccionado:Int = -1
    }


    /**
     * onBindViewHolder() se encarga de coger cada una de las posiciones de la lista de personajes y pasarlas a la clase
     * ViewHolder(clase interna, ver abajo) para que esta pinte todos los valores y active el evento onClick en cada uno.
     * position irá cambiando en cada iteración. Esta invocación a estos métodos lo hace automáticamente,sólo hay que sobreescribirlos
     * y personalizar con nuestro array list.
     * Esta a su vez llama a holder.bind, que está implementado más abajo.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = personajes.get(position)
        holder.bind(item, context, position, this)
    }

    /**
     *  Como su nombre indica lo que hará será devolvernos un objeto ViewHolder al cual le pasamos la celda que hemos creado.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        val viewHolder = ViewHolder(vista)
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, MainActivity2::class.java)
            context.startActivity(intent)
        }

        return viewHolder
    }

    /**
     * getItemCount() nos devuelve el tamaño de la lista, que lo necesita el RecyclerView.
     */
    override fun getItemCount(): Int {
        return personajes.size
    }


    //--------------------------------- Clase interna ViewHolder -----------------------------------
    /**
     * La clase ViewHolder. No es necesaria hacerla dentro del adapter, pero como van tan ligadas
     * se puede declarar aquí.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombrePersonaje = view.findViewById(R.id.txtNombre) as TextView
        val carrilPersonaje = view.findViewById(R.id.txtCarril) as TextView
        val arquetipoPersonaje = view.findViewById(R.id.txtArquetipo) as TextView
        val avatar = view.findViewById(R.id.imgImagen) as ImageView

        val btnDetalleEspcifico = view.findViewById<Button>(R.id.btDetalleCard) as Button
        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        @SuppressLint("ResourceAsColor")
        fun bind(pers: Personaje, context: Context, pos: Int, miAdaptadorRecycler: MiAdaptadorRecycler){
            nombrePersonaje.text = pers.nombre
            carrilPersonaje.text = pers.carril
            arquetipoPersonaje.text = pers.arquetipo

            if (pers.nombre.equals("Khazix")){
                val uri = "@drawable/" + pers.imagen
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }
            if (pers.nombre.equals("Yone")){
                val uri = "@drawable/" + pers.imagen
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }
            if (pers.nombre.equals("Viego")){
                val uri = "@drawable/" + pers.imagen
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }
            if (pers.nombre.equals("Fizz")){
                val uri = "@drawable/" + pers.imagen
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }
            if (pers.nombre.equals("Yasuo")){
                val uri = "@drawable/" + pers.imagen
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }

            if (pos == MiAdaptadorRecycler.seleccionado) {
                with(nombrePersonaje) {
                    this.setTextColor(resources.getColor(R.color.blue))
                }
                carrilPersonaje.setTextColor(R.color.blue)
            }
            else {
                with(nombrePersonaje) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
                carrilPersonaje.setTextColor(R.color.black)
            }

            itemView.setOnClickListener {
                if (pos == MiAdaptadorRecycler.seleccionado){
                    MiAdaptadorRecycler.seleccionado = -1
                }
                else {
                    MiAdaptadorRecycler.seleccionado = pos
                    Log.e("ACSC0", "Seleccionado: ${Almacen.personajes.get(MiAdaptadorRecycler.seleccionado).toString()}")
                }

                miAdaptadorRecycler.notifyDataSetChanged()

                Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecycler.seleccionado.toString(), Toast.LENGTH_SHORT).show()

            }
            itemView.setOnLongClickListener(View.OnLongClickListener {
                Log.e("ACSCO","Seleccionado con long click: ${Almacen.personajes.get(pos).toString()}")
                Almacen.personajes.removeAt(pos)
                miAdaptadorRecycler.notifyDataSetChanged()
                true //Tenemos que devolver un valor boolean.
            })


            btnDetalleEspcifico.setOnClickListener {
                Log.e("Fernando","Has pulsado el botón de ${pers}")
                var inte : Intent = Intent(MainActivity.contextoPrincipal, MainActivity2::class.java)
                inte.putExtra("obj",pers)
                ContextCompat.startActivity(MainActivity.contextoPrincipal, inte, null)
            }
        }
    }
}