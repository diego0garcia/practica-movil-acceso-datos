package ies.sequeros.com.dam.pmdm.administrador.ui.categorias.form

data class CategoriaFormState(
    val nombre: String = "",
    val descripcion: String = "",
    val enabled: Boolean = false,
    val imagePath:String="default",
    // errores (null = sin error)
    val nombreError: String? = null,
    val descripcionError: String? = null,
    val imagePathError:String?=null,

    // para controlar si se intentó enviar (mostrar errores globales)
    val submitted: Boolean = false
)