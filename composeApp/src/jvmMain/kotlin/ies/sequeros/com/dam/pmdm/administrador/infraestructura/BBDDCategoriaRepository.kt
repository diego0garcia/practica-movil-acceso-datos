package ies.sequeros.com.dam.pmdm.administrador.infraestructura

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias.BBDDRepositorioCategoriaJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes.BBDDRepositorioDependientesJava
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.Dependiente
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio


class BBDDCategoriaRepository(
    private val bbddRepositorioCategoriasJava: BBDDRepositorioCategoriaJava
) : ICategoriaRepositorio {
    override suspend fun add(item: Categoria) {
        bbddRepositorioCategoriasJava.add(item)
    }

    override suspend fun remove(item: Categoria): Boolean {
        bbddRepositorioCategoriasJava.remove(item)
        return true
    }
    override suspend fun remove(id: String): Boolean {

        bbddRepositorioCategoriasJava.remove(id)
        return true

    }

    override suspend fun update(item: Categoria): Boolean {

        bbddRepositorioCategoriasJava.update(item)
        return true
    }

    override suspend fun getAll(): List<Categoria> {

        return bbddRepositorioCategoriasJava.all
    }

    override suspend fun findByName(name: String): Categoria? {

        return bbddRepositorioCategoriasJava.findByName( name)
    }
    override suspend fun getById(id: String): Categoria? {
        return bbddRepositorioCategoriasJava.getById(id)
    }
}