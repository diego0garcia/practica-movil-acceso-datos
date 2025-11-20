package ies.sequeros.com.dam.pmdm.administrador.modelo

interface IProductosRepositorio {
    suspend fun add(item: Productos):Unit
    suspend fun remove(item: Productos): Boolean
    suspend fun remove(id:String): Boolean
    suspend fun update(item: Productos): Boolean
    suspend fun getAll():List<Productos>
    suspend fun findByName(name:String): Productos?
    suspend fun getById(id:String):Productos?
}