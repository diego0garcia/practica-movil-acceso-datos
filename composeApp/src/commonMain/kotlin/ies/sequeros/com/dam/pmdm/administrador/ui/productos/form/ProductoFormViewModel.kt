package ies.sequeros.com.dam.pmdm.administrador.ui.productos.form

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.BorrarCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.activar.ActivarCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.actualizar.ActualizarCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.crear.CrearCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.ListarCategoriasUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.DependienteDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.ui.dependientes.form.DependienteFormState
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoFormViewModel (
    val almacenDatos: AlmacenDatos,
    private val item: ProductoDTO?,
    onSuccess: (ProductoFormState) -> Unit): ViewModel()
{

    private val _uiState = MutableStateFlow(
        ProductoFormState(
            nombre = item?.name ?: "",
            imagePath = item?.imagePath ?: "",
            descripcion = item?.description ?: "",
            precio = item?.price ?: "",
            enabled = item?.enabled ?: false,
            categoriaName = item?.categoriaName ?: ""
        )
    )
    val uiState: StateFlow<ProductoFormState> = _uiState.asStateFlow()

    //para saber si el formulario es válido
    val isFormValid: StateFlow<Boolean> = uiState.map { state ->
        if(item==null){
            /*
            println(
                "nombreError=${state.nombreError}, " +
                        "descripcionError=${state.descripcionError}, " +
                        "imagePathError=${state.imagePathError}, " +
                        "categoriaNameError=${state.categoriaNameError}, " +
                        "precioError=${state.precioError}, " +
                        "nombre.isNotBlank=${state.nombre}, " +
                        "precio.isNotBlank=${state.precio}, " +
                        "imagePath.isNotBlank=${state.imagePath}, " +
                        "descripcion.isNotBlank=${state.descripcion}, " +
                        "categoriaName.isNotBlank=${state.categoriaName}, " +
                        "categoriaId=${state.categoriaId}"
            )
             */

            state.nombreError == null &&
                state.descripcionError == null &&
                state.imagePathError ==null &&
                state.categoriaNameError == null &&
                state.precioError == null &&

                state.nombre.isNotBlank() &&
                state.precio.isNotBlank() &&
                state.imagePath.isNotBlank() &&
                state.descripcion.isNotBlank() &&
                state.categoriaName.isNotBlank()
        }else{
            false
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun onNombreChange(v: String) {
        _uiState.value = _uiState.value.copy(nombre = v, nombreError = validateNombre(v))
    }

    fun onCategoriaNameChange(cat: String) {
        _uiState.value = _uiState.value.copy(categoriaName = cat, categoriaNameError = validateCategoriaName(cat))
    }

    fun onCategoriaIdChange(cat: CategoriaDTO?){
        _uiState.value = _uiState.value.copy(categoriaId = cat?.id ?: "")
    }

    fun onDescripcionChange(v: String) {
        _uiState.value = _uiState.value.copy(descripcion = v, descripcionError = validateDescripcion(v))
    }

    fun onImagePathChange(v: String) {
        _uiState.value = _uiState.value.copy(imagePath =  v, imagePathError =  validateImagePath(v))
    }

    fun onPrecioChange(v: String) {
        _uiState.value = _uiState.value.copy(precio =  v, precioError =  validatePrecio(v))
    }

    fun onEnabledChange(v: Boolean) {
        _uiState.value = _uiState.value.copy(
            enabled =  v
        )
    }

    fun clear() {
        _uiState.value = ProductoFormState()
    }

    private fun validateNombre(nombre: String): String? {
        if (nombre.isBlank()) return "El nombre es obligatorio"
        if (nombre.length < 2) return "El nombre es muy corto"
        return null
    }
    private fun validateCategoriaName(categoriaName: String): String? {
        if (categoriaName.isBlank()) return "La categoría es obligatoria"
        return null
    }
    private fun validateImagePath(path: String): String? {
        if (path.isBlank()) return "La imagen es obligatoria"
        return null
    }
    private fun validatePrecio(p: String): String? {
        if (p.isBlank()) return "El precio es obligatorio"
        try {
            val tmp = p.toFloat()
        }catch (e: Exception){
            return "Eso no es un numero perro"
        }
        if (p.toFloat() < 0) return "El precio no puede ser negativo"
        return null
    }

    private fun validateConfirmPassword(pw: String, confirm: String): String? {
        if (confirm.isBlank()) return "Confirma la contraseña"
        if (pw != confirm) return "Las contraseñas no coinciden"
        return null
    }

    private fun validateDescripcion(descripcion: String): String? {
        if (descripcion.isBlank()) return "la descripción es obligatoria"
        //if (descripcion.trim().isNotEmpty()) return "Descripción inválida"
        return null
    }

    fun validateAll(): Boolean {
        val s = _uiState.value
        val nombreErr = validateNombre(s.nombre)
        val descripcionErr = validateDescripcion(s.descripcion)
        val imageErr=validateImagePath(s.imagePath)
        val newState = s.copy(
            nombreError = nombreErr,
            descripcionError = descripcionErr,
            imagePathError = imageErr,
            //categoriaNameError = categoriaErr,

            submitted = true
        )
        _uiState.value = newState
        return listOf(nombreErr, descripcionErr, imageErr).all { it == null }
    }

    //se le pasan lambdas para ejecutar código en caso de éxito o error
    fun submit(
        onSuccess: (ProductoFormState) -> Unit,
        onFailure: ((ProductoFormState) -> Unit)? = null
    ) {
        //se ejecuta en una corrutina, evitando que se bloque la interfaz gráficas
        viewModelScope.launch {
            val ok = validateAll()
            if (ok) {
                onSuccess(_uiState.value)
            } else {
                onFailure?.invoke(_uiState.value)
            }
        }
    }

}