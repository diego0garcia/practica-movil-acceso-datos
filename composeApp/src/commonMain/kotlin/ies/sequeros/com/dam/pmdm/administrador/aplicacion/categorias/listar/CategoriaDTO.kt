package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar

data class CategoriaDTO (var id:String,
                         val name:String,
                         val imagePath:String,
                         val description: String,
                         val enabled: Boolean)