package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar

class ActualizarProductoCommand (var id:String,
                                 val categoriaId:String?,
                                 val name:String,
                                 val imagePath:String,
                                 val description: String,
                                 val price: Float,
                                 val enabled: Boolean,
                                 val categoriaName:String)