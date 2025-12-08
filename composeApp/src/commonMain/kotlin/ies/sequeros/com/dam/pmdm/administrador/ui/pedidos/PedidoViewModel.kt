package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.activar.ActivarCategoriaCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.actualizar.ActualizarCategoriaCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.crear.CrearCategoriaCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.CerrarPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.PedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.activar.ActivarPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.activar.ActivarPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.CrearPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.CrearPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.ListarPedidosUseCase
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.form.CategoriaFormState
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.form.PedidoFormState
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PedidoViewModel(
    //private val administradorViewModel: MainAdministradorViewModel,
    private val pedidoRepositorio: IPedidoRepositorio,
    val almacenDatos: AlmacenDatos
) : ViewModel() {
    //los casos de uso se crean dentro para la recomposición
    //se pueden injectar también, se tratará en próximos temas
    private val cerrarPedidoUseCase: CerrarPedidoUseCase = CerrarPedidoUseCase(pedidoRepositorio,almacenDatos)
    private val crearPedidoUseCase: CrearPedidoUseCase = CrearPedidoUseCase(pedidoRepositorio,almacenDatos)
    private val listarPedidoUseUseCase: ListarPedidosUseCase = ListarPedidosUseCase(pedidoRepositorio,almacenDatos)
    private val activarPedidoUseUseCase: ActivarPedidoUseCase = ActivarPedidoUseCase(pedidoRepositorio,almacenDatos)

    private val _items = MutableStateFlow<MutableList<PedidoDTO>>(mutableListOf())
    val items: StateFlow<List<PedidoDTO>> = _items.asStateFlow()
    private val _selected = MutableStateFlow<PedidoDTO?>(null)
    val selected = _selected.asStateFlow()

    init {

        viewModelScope.launch {
            var items = listarPedidoUseUseCase.invoke()
            _items.value.clear()
            _items.value.addAll(items)

        }
    }

    fun setSelectedPedido(item: PedidoDTO?) {
        _selected.value = item
    }


    fun switchEnablePedido(item: PedidoDTO) {
        val command= ActivarPedidoCommand(
            item.id,
            item.enable,
        )

        viewModelScope.launch {
            val item=activarPedidoUseUseCase.invoke(command)

            _items.value = _items.value.map {
                if (item.id == it.id)
                    item
                else
                    it
            } as MutableList<PedidoDTO>
        }

    }

    fun delete(item: PedidoDTO) {
        viewModelScope.launch {
            cerrarPedidoUseCase.invoke(item.id)
            _items.update { current ->
                current.filterNot { it.id == item.id }.toMutableList()
            }
        }

    }

    fun add(formState: PedidoFormState) {
        val command = CrearPedidoCommand(
            formState.enabled,
            formState.date,
            formState.id_dependiente
        )
        viewModelScope.launch {
            try {
                val user = crearPedidoUseCase.invoke(command)
                _items.value = (_items.value + user) as MutableList<PedidoDTO>
            }catch (e:Exception){
                throw  e
            }

        }
    }



    fun save(item: PedidoFormState) {
        if (_selected.value == null)
            this.add(item)
    }

}