package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.activar


data class ActivarPedidoCommand(
    val id: String,
    val enabled: Boolean,

)