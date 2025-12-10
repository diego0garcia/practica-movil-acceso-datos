package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear

interface IEncryptador {
    fun encriptar(text: String): String
}