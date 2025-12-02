package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.activar


import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.PedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos


class ActivarPedidoUseCase(private val repositorio: IPedidoRepositorio, private val almacenDatos: AlmacenDatos){

    //Es una corrutina, invoke es como se llaman todos los casos de uso
    suspend fun invoke(command: ActivarPedidoCommand ): PedidoDTO {
        val item: Pedido?=repositorio.getById(command.id)
        if (item==null) {
            throw IllegalArgumentException("El pedido no esta registrado.")
        }
        var newUser= item.copy(
            enable = command.enabled,
        )
        repositorio.update(newUser)
        //se devuelve con el path correcto
        return newUser.toDTO(almacenDatos.getAppDataDir()+"/categorias/")
    }
}