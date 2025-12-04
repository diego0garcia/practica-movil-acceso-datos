package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ListarLineaPedidoUseCase(private val repositorio: ILineaPedidoRepositorio, private val almacenDatos: AlmacenDatos) {

    suspend fun invoke( ): List<LineaPedidoDTO> {
        val items = repositorio.getAll().map { it.toDTO() }
        return items
    }
}