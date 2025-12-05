package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido

data class LineaPedidoDTO (var id:String,
                           val product_name: String,
                           val product_price: String,
                           val id_pedido:String)