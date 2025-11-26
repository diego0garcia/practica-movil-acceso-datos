package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ActivarProductoUseCase(private val repositorio: IProductoRepositorio, private val almacenDatos: AlmacenDatos){

    //Es una corrutina, invoke es como se llaman todos los casos de uso
    suspend fun invoke(command: ActivarProductoCommand ): ProductoDTO {
        val item: Producto?=repositorio.getById(command.id)
        if (item==null) {
            throw IllegalArgumentException("El producto no está registrado.")
        }
        var newUser= item.copy(
            enabled = command.enabled,
        )
        repositorio.update(newUser)
        //se devuelve con el path correcto
        return newUser.toDTO(almacenDatos.getAppDataDir()+"/productos/")
    }
}