package ies.sequeros.com.dam.pmdm.administrador.modelo
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.DateAdaptador
import kotlinx.serialization.Serializable
import java.sql.Date

@Serializable
data class Pedido (
    var id:String,
    val enable: Boolean,
    @Serializable(with = DateAdaptador::class)
    val date: Date,
    val id_dependiente: String?
)