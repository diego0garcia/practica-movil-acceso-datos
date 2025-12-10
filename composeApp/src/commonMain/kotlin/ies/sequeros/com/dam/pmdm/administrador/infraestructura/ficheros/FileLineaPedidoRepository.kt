package ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros

import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.serialization.json.Json
import java.io.File

class FileLineaPedidoRepository(
    private val almacenDatos: AlmacenDatos,
    private val fileName: String = "lineapedido.json"
) : ILineaPedidoRepositorio {

    private val subdirectory="/data/"
    init {

    }

    private suspend fun getDirectoryPath(): String {
        val dir = almacenDatos.getAppDataDir()
        val directory = File(dir, subdirectory)

        return directory.absolutePath
    }

    private suspend fun save(items: MutableList<LineaPedido>) {
        if(!File(this.getDirectoryPath()).exists())
            File(this.getDirectoryPath()).mkdirs()
        this.almacenDatos.writeFile(this.getDirectoryPath()+"/"+this.fileName, Json.encodeToString(items))
    }

    override suspend fun add(item: LineaPedido) {
        val items = this.getAll().toMutableList()

        if (items.firstOrNull { it.product_name == item.product_name } == null) {
            items.add(item)
        } else {
            throw IllegalArgumentException("ALTA:El lineapedido con id:" + item.id + " ya existe")
        }
        this.save(items)
    }

    override suspend fun remove(item: LineaPedido): Boolean {
        return this.remove(item.id!!)
    }

    override suspend fun remove(id: String): Boolean {
        val items = this.getAll().toMutableList()
        var item = items.firstOrNull { it.id == id }
        if (item != null) {
            items.remove((item))
            this.save(items)
            return true
        } else {
            throw IllegalArgumentException(
                "BORRADO:" +
                        " El lineapedido con id:" + id + " NO  existe"
            )
        }
        return true
    }

    override suspend fun update(item: LineaPedido): Boolean {
        val items = this.getAll().toMutableList()
        val newItems= items.map { if (it.id == item.id) item else it }.toMutableList()
        this.save(newItems)
        return true
    }


    override suspend fun getAll(): List<LineaPedido> {
        val path = getDirectoryPath()+"/"+this.fileName
        val items= mutableListOf<LineaPedido>()
        var json=""
        if(File(path).exists()) {
            json = almacenDatos.readFile(path)
            if (!json.isEmpty())
                items.addAll(Json.decodeFromString<List<LineaPedido>>(json))
        }
        return items.toList()
    }

    override suspend fun findByName(name: String): LineaPedido? {
        val elements=this.getAll()
        for(element in elements){
            if(element.product_name==name)
                return element
        }
        return null; //this.items.values.firstOrNull { it.name.equals(name) };
    }

    override suspend fun getById(id: String): LineaPedido? {
        val elements=this.getAll()
        for(element in elements){
            if(element.id==id)
                return element
        }
        return null;
    }
}