package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos

import java.sql.Date

data class PedidoDTO (
    var id:String,
    val name:String,
    val imagePath:String,
    val descripcion: String,
    val enable: Boolean,
    val date: Date,
    val id_dependiente: String
)