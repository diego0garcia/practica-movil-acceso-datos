package ies.sequeros.com.dam.pmdm.dependiente.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FormularioLoginFormState (
    val id: String = "",
    val nombre: String = "",
    val contraseña: String = "",
    val nombreError: String? = null,
    val contraseñaError: String? = null,
)

class FormularioLoginViewModel: ViewModel(){
    private val _uiState = MutableStateFlow(FormularioLoginFormState())

    val uiState: StateFlow<FormularioLoginFormState> = _uiState.asStateFlow()

    fun onNombreChange(v: String){
        _uiState.value = _uiState.value.copy(nombre = v, nombreError = validateNombre(v))
    }

    fun setId(id: String){
        _uiState.value = _uiState.value.copy(id = id)
    }

    fun onContraseñaChange(v: String){
        _uiState.value = _uiState.value.copy(contraseña = v, contraseñaError = validatePassword(v))
    }
    private fun validateNombre(nombre: String): String? {
        if (nombre == "") return "El nombre es obligatorio"
        if (nombre.length < 2) return "El nombre es muy corto"
        return null
    }

    private fun validatePassword(pw: String): String? {
        if (pw == "") return "La contraseña es obligatoria"
        if (pw.length < 8) return "La contraseña debe tener al menos 8 caracteres"
        return null
    }

    fun validateAll(c: String, pw: String): Boolean{
        if (validateNombre(c) == null && validatePassword(pw) == null) return true
        return false
    }

    fun accept_loggin(c: String, pw: String) : String{
        if (validateAll(c, pw) == true) return "Usuario creado: ${c}"
        return "" + (if (validateNombre(c) != null) validateNombre(c) else "") + "\n" + (if (validatePassword(pw) != null) validatePassword(pw) else "");
    }

}