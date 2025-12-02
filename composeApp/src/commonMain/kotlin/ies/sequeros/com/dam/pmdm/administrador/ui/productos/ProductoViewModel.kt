package ies.sequeros.com.dam.pmdm.administrador.ui.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.ListarCategoriasUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.BorrarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar.ActivarProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar.ActivarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar.ActualizarProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar.ActualizarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear.CrearProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear.CrearProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ListarProductosUseCase
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.form.ProductoFormState
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductoViewModel(
    //private val administradorViewModel: MainAdministradorViewModel,
    private val productoRepositorio: IProductoRepositorio,
    private val categoriaRepositorio: ICategoriaRepositorio,
    val almacenDatos: AlmacenDatos
) : ViewModel() {
    //los casos de uso se crean dentro para la recomposición
    //se pueden injectar también, se tratará en próximos temas
    private val borrarProductoUseCase: BorrarProductoUseCase
    private val crearProductoUseCase: CrearProductoUseCase
    private val listarProductoUseCase: ListarProductosUseCase
    private val actualizarProductoUseCase: ActualizarProductoUseCase
    private val activarProductoUseCase: ActivarProductoUseCase
    private val listarCategoriasUseCase: ListarCategoriasUseCase

    private val _items = MutableStateFlow<MutableList<ProductoDTO>>(mutableListOf())
    val items: StateFlow<List<ProductoDTO>> = _items.asStateFlow()
    private val _selected = MutableStateFlow<ProductoDTO?>(null)
    val selected = _selected.asStateFlow()

    //ESTO ES PA CARGAR LAS CATEGORIAS PARA LUEGO SELECCIONARLAS EN LO DEL CREAR PRODUCTO
    private val _categoria = MutableStateFlow<MutableList<CategoriaDTO>>(mutableListOf())
    val categoria: StateFlow<List<CategoriaDTO>> = _categoria.asStateFlow()
    private val _categoriaSelected = MutableStateFlow<CategoriaDTO?>(null)
    val categoriaSelected = _categoriaSelected.asStateFlow()

    init {
        actualizarProductoUseCase = ActualizarProductoUseCase(productoRepositorio,almacenDatos)
        borrarProductoUseCase = BorrarProductoUseCase(productoRepositorio,almacenDatos)
        crearProductoUseCase = CrearProductoUseCase(productoRepositorio,almacenDatos)
        listarProductoUseCase = ListarProductosUseCase(productoRepositorio,almacenDatos)
        activarProductoUseCase = ActivarProductoUseCase(productoRepositorio,almacenDatos)

        listarCategoriasUseCase = ListarCategoriasUseCase(categoriaRepositorio,almacenDatos)
        viewModelScope.launch {
            val categoria = listarCategoriasUseCase.invoke()
            _categoria.value.clear()
            _categoria.value.addAll(categoria)

            var items = listarProductoUseCase.invoke()
            _items.value.clear()
            _items.value.addAll(items)

        }

    }

    fun setCategoriaSeleccionada(cat: CategoriaDTO) {
        _categoriaSelected.value = cat
    }

    fun setSelectedProducto(item: ProductoDTO?) {
        _selected.value = item
    }


    fun switchEnableProducto(item: ProductoDTO) {
        val command= ActivarProductoCommand(
            item.id,
            item.enabled,
        )

        viewModelScope.launch {
            val item=activarProductoUseCase.invoke(command)

            _items.value = _items.value.map {
                if (item.id == it.id)
                    item
                else
                    it
            } as MutableList<ProductoDTO>
        }

    }

    fun delete(item: ProductoDTO) {
        viewModelScope.launch {
            borrarProductoUseCase.invoke(item.id)
            _items.update { current ->
                current.filterNot { it.id == item.id }.toMutableList()
            }
        }

    }

    fun add(formState: ProductoFormState) {
        val command = CrearProductoCommand(
            formState.nombre,
            formState.imagePath,
            formState.descripcion,
            formState.precio.toFloat(),
            formState.enabled,
            formState.categoriaName,
            formState.categoriaId
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

    fun update(formState: ProductoFormState) {
        val command = ActualizarProductoCommand(
            selected.value!!.id!!,
            selected.value!!.categoriaId!!,
            formState.nombre,
            formState.imagePath,
            formState.descripcion,
            formState.precio.toFloat(),
            formState.enabled,
            formState.categoriaName
        )
        viewModelScope.launch {
            val item = actualizarProductoUseCase.invoke(command)
            _items.update { current ->
                current.map { if (it.id == item.id) item else it } as MutableList<ProductoDTO>
            }
        }


    }

    fun save(item: ProductoFormState) {
        if (_selected.value == null)
            this.add(item)
        else
            this.update(item)
    }



}