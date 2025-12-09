package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID

class CrearLineaPedidoUseCase(private val repositorio: ILineaPedidoRepositorio, private val almacenDatos: AlmacenDatos)  {

    suspend  fun invoke(createLineaProductoCommand: CrearLineaPedidoCommand): LineaPedidoDTO {
        //this.validateUser(user)
        val id=generateUUID()
        val item = LineaPedido(
            id = id,
            product_name =  createLineaProductoCommand.product_name,//createUserCommand.categoriaId,
            product_price = createLineaProductoCommand.product_price,
            id_pedido = createLineaProductoCommand.id_pedido
        )
        repositorio.add(item)
        return item.toDTO( almacenDatos.getAppDataDir()+"/lineapedido/");
    }
}