package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias

data class CategoriaDTO (var id:String,
                         val name:String,
                         val imagePath:String,
                         val descripcion: String,
                         val enabled: Boolean)
