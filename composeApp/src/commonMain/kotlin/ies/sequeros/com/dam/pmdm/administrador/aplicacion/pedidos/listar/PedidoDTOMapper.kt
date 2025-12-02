package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.PedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido

fun Pedido.toDTO(path:String="") = PedidoDTO(
    id = id,
    name = name,
    imagePath = imagePath,
    descripcion = descripcion,
    enable = enable,
    date = date,
    id_dependiente = id_dependiente
)
fun PedidoDTO.toPedido()= Pedido(
    id = id,
    name = name,
    imagePath = imagePath,
    descripcion = descripcion,
    enable = enable,
    date = date,
    id_dependiente = id_dependiente
)