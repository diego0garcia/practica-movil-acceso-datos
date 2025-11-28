package ies.sequeros.com.dam.pmdm.administrador.infraestructura

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedidos.BBDDRepositorioPedidoJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.productos.BBDDRepositorioProductoJava
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto


class BBDDPedidoRepository(
    private val bbddRepositorioPedidoJava: BBDDRepositorioPedidoJava
) : IPedidoRepositorio {
    override suspend fun add(item: Pedido) {
        bbddRepositorioPedidoJava.add(item)
    }

    override suspend fun remove(item: Pedido): Boolean {
        bbddRepositorioPedidoJava.remove(item)
        return true
    }
    override suspend fun remove(id: String): Boolean {

        bbddRepositorioPedidoJava.remove(id)
        return true

    }

    override suspend fun update(item: Pedido): Boolean {

        bbddRepositorioPedidoJava.update(item)
        return true
    }

    override suspend fun getAll(): List<Pedido> {

        return bbddRepositorioPedidoJava.all
    }

    override suspend fun findByName(name: String): Pedido? {

        return bbddRepositorioPedidoJava.findByName( name)
    }

    override suspend fun getById(id: String): Pedido? {
        return bbddRepositorioPedidoJava.getById(id)
    }
}