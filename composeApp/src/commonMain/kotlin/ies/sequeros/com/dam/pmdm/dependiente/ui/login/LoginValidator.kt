package ies.sequeros.com.dam.pmdm.dependiente.ui.login

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.IEncryptador
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio

class LoginValidator(
    private val dependienteRepositorio: IDependienteRepositorio,
    private val formularioLoginViewModel: FormularioLoginViewModel,
    private val encrytador: IEncryptador
) {

    suspend fun validar(nombre: String, contraseña: String, soloAdmins: Boolean = false): String {
        //val contraseñaEncriptada = encrytador.encriptar(contraseña)
        //print(contraseñaEncriptada)

        val user = dependienteRepositorio
            .getAll()
            .firstOrNull { it.name.equals(nombre, true) || it.email.equals(nombre, true) }

        if (user == null) return "Usuario o contraseña incorrectos"
        if (!user.enabled) return "Usuario deshabilitado"
        if (user.password != contraseña /*&& user.password != contraseñaEncriptada*/) return "Usuario o contraseña incorrectos"

        if (soloAdmins && !user.isAdmin) return "Este usuario no es administrador"

        //AQUÍ COGEMOS EL ID DEL USUARIO Y LO AÑADIMOS AL VIEWMODEL PARA
        // PODER USARLO LUEGO AL ENTREGAR EL PRODUCTO SABER QUE USARIO LO ENTREGÓ
        formularioLoginViewModel.setId(user.id)
        return ""
    }
}