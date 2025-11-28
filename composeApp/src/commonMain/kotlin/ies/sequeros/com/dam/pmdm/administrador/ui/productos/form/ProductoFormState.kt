package ies.sequeros.com.dam.pmdm.administrador.ui.productos.form

data class ProductoFormState(
    val nombre: String = "",
    val imagePath:String="default",
    val descripcion: String = "",
    val precio: String = "",
    val enabled: Boolean = false,
    val categoriaName: String = "",
    // errores (null = sin error)
    val nombreError: String? = null,
    val imagePathError:String?=null,
    val descripcionError: String? = null,
    val precioError: String? = null,
    val categoriaNameError: String? = null,

    // para controlar si se intentó enviar (mostrar errores globales)
    val submitted: Boolean = false
)