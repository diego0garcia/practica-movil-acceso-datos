package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto

fun LineaPedido.toDTO(path:String="") = LineaPedidoDTO(
    id = id,
    product_name = product_name,
    product_price = product_price.toString(),
    id_pedido = id_pedido
)
fun LineaPedidoDTO.toProducto()= LineaPedido(
    id = id,
    product_name = product_name,
    product_price = product_price.toFloat(),
    id_pedido = id_pedido
)