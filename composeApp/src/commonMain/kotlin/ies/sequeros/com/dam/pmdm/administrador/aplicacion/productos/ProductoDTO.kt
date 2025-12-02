package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos

data class ProductoDTO (var id:String,
                           val categoriaId:String,
                           val name:String,
                           val imagePath:String,
                           val description: String,
                           val price: String,
                           val enabled: Boolean,
                           val categoriaName:String)