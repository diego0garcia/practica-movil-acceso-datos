package ies.sequeros.com.dam.pmdm.tpv.ui.pedido

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.form.CategoriaFormState

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VisualizarPedidoViewModel (private val item: LineaPedidoDTO?)/*,
                                 onSuccess: (CategoriaFormState) -> Unit): ViewModel()*/{

    /*
    private val _uiState = MutableStateFlow(
        CategoriaFormState(
            nombre = item?.name ?: "",
            imagePath = item?.imagePath ?: "",
            descripcion = item?.descripcion ?: "",
            enabled = item?.enabled ?: false

        )
    )
    val uiState: StateFlow<CategoriaFormState> = _uiState.asStateFlow()

    //para saber si el formulario es válido
    val isFormValid: StateFlow<Boolean> = uiState.map { state ->
        if(item==null) {
            state.nombreError == null &&
                    state.imagePathError == null &&
                    state.descripcionError == null &&

                    state.nombre.isNotBlank() &&
                    state.imagePath.isNotBlank() &&
                    state.descripcion.isNotBlank()
        }else{
            state.nombreError == null &&
                    state.imagePathError ==null &&
                    state.nombre.isNotBlank() &&
                    state.descripcion.isNotBlank() &&
                    state.imagePath.isNotBlank()

        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun onNombreChange(v: String) {
        _uiState.value = _uiState.value.copy(nombre = v, nombreError = validateNombre(v))
    }

    fun onDescripcionChange(v: String) {
        _uiState.value = _uiState.value.copy(descripcion = v, descripcionError = validateNombre(v))
    }

    fun onImagePathChange(v: String) {
        _uiState.value = _uiState.value.copy(imagePath =  v, imagePathError =  validateImagePath(v))
    }

    fun onEnabledChange(v: Boolean) {
        _uiState.value = _uiState.value.copy(
            enabled =  v
        )
    }

    fun clear() {
        _uiState.value = CategoriaFormState()
    }

    private fun validateNombre(nombre: String): String? {
        if (nombre.isBlank()) return "El nombre es obligatorio"
        if (nombre.length < 2) return "El nombre es muy corto"
        return null
    }

    private fun validateImagePath(path: String): String? {
        if (path.isBlank()) return "La imagen es obligatoria"
        return null
    }

    private fun validateDescripcion(descripcion: String): String? {
        if (descripcion.isBlank()) return "La descripcion es obligatoria"
        return null
    }


    fun validateAll(): Boolean {
        val s = _uiState.value
        val nombreErr = validateNombre(s.nombre)
        val desErr = if(item==null) validateDescripcion(s.descripcion) else null
        val imageErr=validateImagePath(s.imagePath)
        val newState = s.copy(
            nombreError = nombreErr,
            imagePathError = imageErr,
            descripcionError = desErr,

            submitted = true
        )
        _uiState.value = newState
        return listOf(nombreErr, imageErr, desErr).all { it == null }
    }

    //se le pasan lambdas para ejecutar código en caso de éxito o error
    fun submit(
        onSuccess: (CategoriaFormState) -> Unit,
        onFailure: ((CategoriaFormState) -> Unit)? = null
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

     */

}