package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear

import java.sql.Date


data class CrearPedidoCommand (
    val id: String,
    val enable: Boolean,
    val date: Date,
    val id_dependiente:String?
)