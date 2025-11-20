package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable
@Serializable
data class LineaPedido (
    var id:String,
    val name:String,
    val imagePath:String,

    )