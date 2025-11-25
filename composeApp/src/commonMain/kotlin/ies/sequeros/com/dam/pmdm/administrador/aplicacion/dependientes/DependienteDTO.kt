package ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes

data class DependienteDTO (val id:String,
                           val name:String,
                           val email:String,
                           val imagePath:String,
                           val enabled: Boolean,
                           val isAdmin:Boolean)