package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID

class CrearProductoUseCase(private val repositorio: IProductoRepositorio, private val almacenDatos: AlmacenDatos)  {

    suspend  fun invoke(createProductoCommand: CrearProductoCommand): ProductoDTO {
        //this.validateUser(user)
        if (repositorio.findByName(createProductoCommand.name)!=null) {
            throw IllegalArgumentException("El producto ya está registrado.")
        }
        val id=generateUUID()
        val imageName=almacenDatos.copy(createProductoCommand.imagePath,id,"/productos/")
        val item = Producto(
            id = id,
            //ACUERDATE DE CAMBIAR ESTO PARA CREAR PRODUCTOS PORFAVOR QUE NO SE TE OLVIDE HDP
            categoriaId =  createProductoCommand.categoriaId,//createUserCommand.categoriaId,
            name = createProductoCommand.name,
            imagePath = imageName,
            description = createProductoCommand.description,
            price = createProductoCommand.price,
            enabled = createProductoCommand.enabled,
            categoriaName = createProductoCommand.categoriaName,
        )
        val element=repositorio.findByName(item.name)
        if(element!=null)
            throw IllegalArgumentException("El producto ya está registrado.")
        repositorio.add(item)
        return item.toDTO( almacenDatos.getAppDataDir()+"/productos/");
    }
}