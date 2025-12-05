package ies.sequeros.com.dam.pmdm.dependiente.ui

import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio

class LoginValidator(
    private val dependienteRepositorio: IDependienteRepositorio
) {

    suspend fun validar(nombre: String, contraseña: String, soloAdmins: Boolean = false): String {

        val user = dependienteRepositorio
            .getAll()
            .firstOrNull { it.name.equals(nombre, true) || it.email.equals(nombre, true) }

        if (user == null) return "Usuario o contraseña incorrectos"
        if (!user.enabled) return "Usuario deshabilitado"
        if (user.password != contraseña) return "Usuario o contraseña incorrectos"

        if (soloAdmins && !user.isAdmin) return "Este usuario no es administrador"

        return ""
    }
}