package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable
@Serializable
data class Pedido (
    var id:String,
    val name:String,
    val imagePath:String,

)