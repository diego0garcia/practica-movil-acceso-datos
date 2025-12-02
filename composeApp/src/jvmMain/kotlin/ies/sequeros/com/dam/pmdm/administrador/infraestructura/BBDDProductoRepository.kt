package ies.sequeros.com.dam.pmdm.administrador.infraestructura

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.productos.BBDDRepositorioProductoJava
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto


class BBDDProductoRepository(
    private val bbddRepositorioProductoJava: BBDDRepositorioProductoJava
) : IProductoRepositorio {
    override suspend fun add(item: Producto) {
        bbddRepositorioProductoJava.add(item)
    }

    override suspend fun remove(item: Producto): Boolean {
        bbddRepositorioProductoJava.remove(item)
        return true
    }
    override suspend fun remove(id: String): Boolean {
        bbddRepositorioProductoJava.remove(id)
        return true
    }

    override suspend fun update(item: Producto): Boolean {
        bbddRepositorioProductoJava.update(item)
        return true
    }

    override suspend fun getAll(): List<Producto> {
        return bbddRepositorioProductoJava.all
    }

    override suspend fun findByName(name: String): Producto? {
        return bbddRepositorioProductoJava.findByName( name)
    }
    override suspend fun getById(id: String): Producto? {
        return bbddRepositorioProductoJava.getById(id)
    }
}