package modelo

object AlmacenUsuarios {
    var usuarios = ArrayList<Usuario>()

    fun aniadirUsuario(p:Usuario){
        usuarios.add(p)
    }
}