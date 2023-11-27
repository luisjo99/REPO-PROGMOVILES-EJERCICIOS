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
        registro.put("email", e.email)
        registro.put("nombre", e.nombre)
        registro.put("weight", e.weight)
        registro.put("date", e.date)
        registro.put("imagen", e.imagen)

        val codigo = bd.insert("ejercicios", null, registro)
        bd.close()
        return codigo
    }

    fun delEjercicio(contexto: AppCompatActivity, nombre: String, userEmail: String): Int {
        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase

        val cant = bd.delete("ejercicios", "nombre=? AND email=?", arrayOf(nombre, userEmail))
        bd.close()
        return cant
    }

    fun modEjercicio(contexto: AppCompatActivity, nombre: String, e: Ejercicio): Int {
        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase

        val registro = ContentValues()
        registro.put("weight", e.weight)
        registro.put("email", e.email)
        registro.put("date", e.date)
        registro.put("imagen", e.imagen)

        val cant = bd.update("ejercicios", registro, "nombre=? AND email=?", arrayOf(nombre, e.email))
        bd.close()
        return cant
    }

    fun buscarEjercicio(contexto: AppCompatActivity, nombre: String, userEmail: String): Ejercicio? {
        var e: Ejercicio? = null
        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase

        val consulta = "SELECT email, weight, date, imagen FROM ejercicios WHERE nombre=? AND email=?"
        val parametros = arrayOf(nombre, userEmail)
        val fila = bd.rawQuery(consulta, parametros)

        if (fila.moveToFirst()) {
            e = Ejercicio(nombre, fila.getString(0), fila.getDouble(1), fila.getString(2), fila.getString(3))
        }

        bd.close()
        return e
    }

    fun obtenerEjercicios(contexto: AppCompatActivity, userEmail: String): ArrayList<Ejercicio> {
        val ejercicios: ArrayList<Ejercicio> = ArrayList()

        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase

        val consulta = "SELECT email, nombre, weight, date, imagen FROM ejercicios WHERE email = ?"
        val parametros = arrayOf(userEmail)
        val fila = bd.rawQuery(consulta, parametros)
        while (fila.moveToNext()) {
            var e: Ejercicio = Ejercicio(fila.getString(0),fila.getString(1),fila.getDouble(2),fila.getString(3), fila.getString(4))
            ejercicios.add(e)
        }
        bd.close()
        return ejercicios //este arrayList lo puedo poner en un adapter de un RecyclerView por ejemplo.
    }
}