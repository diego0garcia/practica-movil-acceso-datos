package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable
@Serializable
data class Producto (
    var id:String,
    val categoriaId:String,
    val name:String,
    val imagePath:String,
    val description: String,
    val price: Float,
    val enabled: Boolean,
    val categoriaName:String
)