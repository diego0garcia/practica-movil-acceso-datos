package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.form

import java.time.Instant
import java.sql.Date

data class PedidoFormState(
    val enabled: Boolean = false,
    val date: Date = Date(Instant.now().toEpochMilli()),
    val id_dependiente:String = "",

    // errores (null = sin error)
    val nombreError: String? = null,
    val descripcionError: String? = null,
    val imagePathError:String?=null,
    val id_dependienteError:String?=null,

    // para controlar si se intentó enviar (mostrar errores globales)
    val submitted: Boolean = false
)