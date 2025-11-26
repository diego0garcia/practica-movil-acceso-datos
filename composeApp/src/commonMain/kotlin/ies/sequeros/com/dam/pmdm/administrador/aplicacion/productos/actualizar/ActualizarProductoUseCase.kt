package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ActualizarProductoUseCase(private val repositorio: IProductoRepositorio, private val almacenDatos: AlmacenDatos) {

    suspend fun invoke(command: ActualizarProductoCommand, ): ProductoDTO {

        val item: Producto?=repositorio.getById(command.id)

        var nuevaImagePath:String?=null
        if (item==null) {
            throw IllegalArgumentException("El producto no está registrado.")
        }
        //se pasa a dto para tener el path
        var itemDTO: ProductoDTO=item.toDTO(almacenDatos.getAppDataDir()+"/productos/")

        //si las rutas son diferentes se borra y se copia
        if(itemDTO.imagePath!=command.imagePath) {
            almacenDatos.remove(itemDTO.imagePath)
            nuevaImagePath=almacenDatos.copy(command.imagePath,command.id,"/productos/")
        }else{
            nuevaImagePath=item.imagePath
        }

        var newUser= item.copy(
            categoriaId = command.categoriaId,
            name = command.name,
            imagePath = nuevaImagePath,
            description = command.description,
            price = command.price,
            enabled = command.enabled,
            categoriaName = command.categoriaName,
        )
        repositorio.update(newUser)
        //se devuelve con el path correcto
        return newUser.toDTO(almacenDatos.getAppDataDir()+"/productos/")
    }
}