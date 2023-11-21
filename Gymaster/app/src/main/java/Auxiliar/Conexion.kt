package Auxiliar

import Conexion.AdminSQLiteConexion
import Modelo.Ejercicio
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity

object Conexion {

    //Clase tipo Singleton para acceder e métodos sin tener que crear objeto (similar a Static de Java)
    //Si hay algún cambio en la BBDD, se cambia el número de versión y así automáticamente
    // se pasa por el OnUpgrade del AdminSQLite
    //y ahí añades las sentencias que interese modificar de la BBDD
    private  var DATABASE_NAME = "administracion.db"
    private  var DATABASE_VERSION = 1


    fun cambiarBD(nombreBD:String){
        this.DATABASE_NAME = nombreBD
    }

    fun addEjercicio(contexto: AppCompatActivity, e: Ejercicio):Long{
        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase //habilito la BBDD para escribir en ella, tambié deja leer.
        val registro = ContentValues() //objeto de kotlin, contenido de valores, un Map. Si haceis ctrl+clic lo veis.
        registro.put("nombre",e.nombre)
        registro.put("weight", e.weight)
        registro.put("date", e.date)
        registro.put("imagen", e.imagen)
        val codigo = bd.insert("ejercicios", null, registro)
        bd.close()
        return codigo
    }

    fun delEjercicio(contexto: AppCompatActivity, nombre: String):Int{
        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        //val cant = bd.delete("personas", "dni='${dni}'", null)
        val cant = bd.delete("ejercicios", "nombre=?", arrayOf(nombre))
        bd.close()
        return cant
    }

    fun modEjercicio(contexto: AppCompatActivity, nombre: String, e: Ejercicio):Int {
        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("weight", e.weight)
        registro.put("date", e.date)
        registro.put("imagen", e.imagen)
        // val cant = bd.update("personas", registro, "dni='${dni}'", null)
        val cant = bd.update("ejercicios", registro, "nombre=?", arrayOf(nombre))
        //val cant = bd.update("personas", registro, "dni=? AND activo=?", arrayOf(dni.toString(), activo.toString()))
        //Esta línea de más arriba es para tener un ejemplo si el where tuviese más condiciones
        //es mejor la forma de la línea 43 que la de la línea 42, ya que es peligroso inyectar sql directamente al controlarse peor los errores
        //cant trae los datos actualizados.
        bd.close()
        return cant
    }

    fun buscarEjercicio(contexto: AppCompatActivity, nombre:String): Ejercicio? {
        var e: Ejercicio? = null //si no encuentra ninguno vendrá null, por eso la ? y también en la devolución de la función.
        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase
        /*Esta funciona pero es mejor como está hecho justo debajo con ?
        val fila = bd.rawQuery(
            "select nombre,edad from personas where dni='${dni}'",
            null
        )*/
        val fila =bd.rawQuery(
            "SELECT weight, date, imagen FROM ejercicios WHERE nombre=?",
            arrayOf(nombre)
        )
        //en fila viene un CURSOR, que está justo antes del primero por eso lo ponemos en el primero en la siguiente línea
        if (fila.moveToFirst()) {//si no hay datos el moveToFirst, devuelve false, si hay devuelve true.
            e = Ejercicio(nombre, fila.getDouble(0), fila.getString(1), fila.getString(2))
        }
        bd.close()
        return e
    }

    fun obtenerEjercicios(contexto: AppCompatActivity):ArrayList<Ejercicio>{
        var ejercicios:ArrayList<Ejercicio> = ArrayList(1)

        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase
        val fila = bd.rawQuery("select nombre, weight, date, imagen from ejercicios", null)
        while (fila.moveToNext()) {
            var e: Ejercicio = Ejercicio(fila.getString(0),fila.getDouble(1),fila.getString(2), fila.getString(3))
            ejercicios.add(e)
        }
        bd.close()
        return ejercicios //este arrayList lo puedo poner en un adapter de un RecyclerView por ejemplo.
    }
}