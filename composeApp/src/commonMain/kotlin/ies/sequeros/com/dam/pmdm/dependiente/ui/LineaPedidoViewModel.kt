package ies.sequeros.com.dam.pmdm.dependiente.ui

import androidx.lifecycle.ViewModel
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido

class LineaPedidoViewModel(private val repositorio: ILineaPedidoRepositorio) : ViewModel() {
    suspend fun findByPedido(idPedido: String): List<LineaPedido> {
        return repositorio.getAll().filter { it.id_pedido == idPedido }
    }
}
