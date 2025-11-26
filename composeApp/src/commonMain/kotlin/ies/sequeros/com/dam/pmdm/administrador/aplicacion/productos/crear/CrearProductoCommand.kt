package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear

class CrearProductoCommand (
    var id:String,
    val categoriaId:String?,
    val name:String,
    val imagePath:String,
    val description: String,
    val price: Float,
    val enabled: Boolean,
    val categoriaName:String
)