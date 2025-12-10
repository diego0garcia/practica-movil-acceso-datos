package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.entregar


data class EntregarPedidoCommand(
    val id: String,
    val enabled: Boolean,
    val id_dependiente: String
)