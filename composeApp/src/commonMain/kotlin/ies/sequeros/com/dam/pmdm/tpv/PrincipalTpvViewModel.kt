package ies.sequeros.com.dam.pmdm.tpv

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.ProductoDTO

class PrincipalTpvViewModel : ViewModel() {
    fun exit() {
    }

    private val _pedido = mutableStateListOf<ProductoDTO>()
    val pedido: List<ProductoDTO> = _pedido

    fun añadirProducto(producto: ProductoDTO) {
        _pedido.add(producto)  // Compose detecta automáticamente el cambio
    }

    fun borrarPedido() {
        _pedido.clear()
    }
}