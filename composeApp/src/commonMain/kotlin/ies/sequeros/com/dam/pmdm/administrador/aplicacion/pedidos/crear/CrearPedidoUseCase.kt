package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.PedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class CrearPedidoUseCase(private val repositorio: IPedidoRepositorio, private val almacenDatos: AlmacenDatos)  {

    suspend  fun invoke(createUserCommand: CrearPedidoCommand): PedidoDTO {
        //this.validateUser(user)

        //val id=generateUUID()
        val item = Pedido(
            id = createUserCommand.id,
            enable = true,
            date = createUserCommand.date,
            id_dependiente = createUserCommand.id_dependiente
        )
        val element=repositorio.findByName(item.id)
        if(element!=null)
            throw IllegalArgumentException("El pedido ya está registrado.")
        repositorio.add(item)
        return item.toDTO( almacenDatos.getAppDataDir()+"/pedidos/");
    }
}