package ies.sequeros.com.dam.pmdm.tpv

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear.CrearLineaPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear.CrearProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.form.ProductoFormState
import ies.sequeros.com.dam.pmdm.tpv.ui.pedido.LineaPedidoFormState
import kotlinx.coroutines.launch

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

    fun add(formState: LineaPedidoFormState) {
        val command = CrearLineaPedidoCommand(
            formState.product_name,
            formState.product_price.toFloat(),
            formState.id_pedido,
        )
        viewModelScope.launch {
            try {
                val user = crearProductoUseCase.invoke(command)
                _items.value = (_items.value + user) as MutableList<ProductoDTO>
            }catch (e:Exception){
                throw  e
            }
        }
    }
}