package ies.sequeros.com.dam.pmdm.tpv

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear.CrearLineaPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.PedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.CrearPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.CrearPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.ui.lineapedido.LineaPedidoViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.PedidoViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.form.PedidoFormState
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID
import ies.sequeros.com.dam.pmdm.tpv.ui.pedido.LineaPedidoFormState
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.Instant

class PrincipalTpvViewModel(
    val pedidoViewModel: PedidoViewModel,
    val lineaPedidoViewModel: LineaPedidoViewModel,

    private  val lineaPedidoRepository: ILineaPedidoRepositorio,
    private val pedidoRepositorio: IPedidoRepositorio,
    val almacenDatos: AlmacenDatos
) : ViewModel() {
    var dependienteSelected by mutableStateOf<String>("")
    //private val pedidoFormState: PedidoFormState
    private val crearPedidoUseCase: CrearPedidoUseCase
    private val _pedido = mutableStateListOf<ProductoDTO>()
    val pedido: List<ProductoDTO> = _pedido
    var pedidoFormState by mutableStateOf(
        PedidoFormState(
            id_dependiente = ""
        )
    )


    init {
        crearPedidoUseCase = CrearPedidoUseCase(pedidoRepositorio,almacenDatos)


    }

    fun exit() {
    }

    fun actualizarDependiente(id: String) {
        pedidoFormState = pedidoFormState.copy(id_dependiente = id)
    }

    fun borrarLinea(index: Int) {
        _pedido.removeAt(index)
    }

    fun añadirProducto(producto: ProductoDTO) {
        _pedido.add(producto)  // Compose detecta automáticamente el cambio
    }

    fun borrarPedido() {
        _pedido.clear()
    }

    fun borrarLineaPedido() {
        _pedido.clear()
    }

    fun add(pedidoID: String) {
        pedidoViewModel.add(
            pedidoFormState, pedidoID
        )

        pedido.forEach { pedido ->
            var lineaPedidoFormState = LineaPedidoFormState(
                product_name = pedido.name,
                product_price = pedido.price,
                id_pedido = pedidoID
            )
            lineaPedidoViewModel.add(lineaPedidoFormState)
        }

        /*
        val pedidoCommand = CrearPedidoCommand(
            id = pedidoID,
            true,
            Date.from(Instant.now()) as Date,
            "admin"
        )

        val command = CrearLineaPedidoCommand(
            lineaPedidoFormState.product_name,
            lineaPedidoFormState.product_price.toFloat(),
            lineaPedidoFormState.id_pedido,
        )

        viewModelScope.launch {
            try {
                val user = crearPedidoUseCase.invoke(pedidoCommand)
                pedidoViewModel._items.value = (pedidoViewModel._items.value + user) as MutableList<PedidoDTO>
            }catch (e:Exception){
                throw  e
            }
        }
         */

    }
}