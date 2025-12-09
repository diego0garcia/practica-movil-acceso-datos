package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.activar.ActivarCategoriaCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.actualizar.ActualizarCategoriaCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.crear.CrearCategoriaCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.BorrarLineaPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear.CrearLineaPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear.CrearLineaPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar.ListarLineaPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.CerrarPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.PedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.activar.ActivarPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.activar.ActivarPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.CrearPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.CrearPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.ListarPedidosUseCase
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.form.CategoriaFormState
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.form.PedidoFormState
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID
import ies.sequeros.com.dam.pmdm.tpv.ui.pedido.LineaPedidoFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LineaPedidoViewModel(
    //private val administradorViewModel: MainAdministradorViewModel,
    private val lineaPedidoRepositorio: ILineaPedidoRepositorio,
    val almacenDatos: AlmacenDatos
) : ViewModel() {
    //los casos de uso se crean dentro para la recomposición
    //se pueden injectar también, se tratará en próximos temas
    private val borrarLineaPedidoUseCase: BorrarLineaPedidoUseCase
    private val crearLineaPedidoUseCase: CrearLineaPedidoUseCase
    private val listarLineaPedidoUseCase: ListarLineaPedidoUseCase

    private val _items = MutableStateFlow<MutableList<LineaPedidoDTO>>(mutableListOf())
    val items: StateFlow<List<LineaPedidoDTO>> = _items.asStateFlow()
    private val _selected = MutableStateFlow<LineaPedidoDTO?>(null)
    val selected = _selected.asStateFlow()

    init {
        borrarLineaPedidoUseCase = BorrarLineaPedidoUseCase(lineaPedidoRepositorio,almacenDatos)
        crearLineaPedidoUseCase = CrearLineaPedidoUseCase(lineaPedidoRepositorio,almacenDatos)
        listarLineaPedidoUseCase = ListarLineaPedidoUseCase(lineaPedidoRepositorio,almacenDatos)


        viewModelScope.launch {
            var items = listarLineaPedidoUseCase.invoke()
            _items.value.clear()
            _items.value.addAll(items)

        }
    }

    fun setSelectedPedido(item: LineaPedidoDTO?) {
        _selected.value = item
    }


    fun delete(item: PedidoDTO) {
        viewModelScope.launch {
            borrarLineaPedidoUseCase.invoke(item.id)
            _items.update { current ->
                current.filterNot { it.id == item.id }.toMutableList()
            }
        }

    }

    fun add(formState: LineaPedidoFormState) {
        val command = CrearLineaPedidoCommand(
            formState.product_name,
            formState.product_price.toFloat(),
            formState.id_pedido
        )
        viewModelScope.launch {
            try {
                val user = crearLineaPedidoUseCase.invoke(command)
                _items.value = (_items.value + user) as MutableList<LineaPedidoDTO>
            }catch (e:Exception){
                throw  e
            }

        }
    }



    fun save(item: LineaPedidoFormState) {
        if (_selected.value == null)
            this.add(item)
    }

}