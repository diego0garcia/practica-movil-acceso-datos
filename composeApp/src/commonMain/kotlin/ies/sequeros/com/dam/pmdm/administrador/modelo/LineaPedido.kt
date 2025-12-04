package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable
@Serializable
data class LineaPedido (
    var id:String,
    val product_name:String,
    val product_price:Float,
    val id_pedido:String,
    )