package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar

import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido

fun Pedido.toDTO(path:String="") = PedidoDTO(
    id = id,
    name = name,
    imagePath = imagePath,
)
fun PedidoDTO.toPedido()= Pedido(
    id = id,
    name = name,
    imagePath = imagePath,
)