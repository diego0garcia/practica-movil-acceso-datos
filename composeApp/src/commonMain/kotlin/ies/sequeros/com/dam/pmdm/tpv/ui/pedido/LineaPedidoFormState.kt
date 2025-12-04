package ies.sequeros.com.dam.pmdm.tpv.ui.pedido

data class LineaPedidoFormState(
    val product_name: String = "",
    val product_price: String = "",
    val id_pedido: String = "",

    // para controlar si se intentó enviar (mostrar errores globales)
    val submitted: Boolean = false
)