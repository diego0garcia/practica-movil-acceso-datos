package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable
@Serializable
data class Categorias (
    var id:String,
    val name:String,
    val imagePath:String,
    val description: String,
    val enabled: Boolean
)