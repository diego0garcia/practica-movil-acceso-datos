package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID

class CrearProductoUseCase(private val repositorio: IProductoRepositorio, private val almacenDatos: AlmacenDatos)  {

    suspend  fun invoke(createUserCommand: CrearProductoCommand): ProductoDTO {
        //this.validateUser(user)
        if (repositorio.findByName(createUserCommand.name)!=null) {
            throw IllegalArgumentException("El producto ya está registrado.")
        }
        val id=generateUUID()
        val imageName=almacenDatos.copy(createUserCommand.imagePath,id,"/productos/")
        val item = Producto(
            id = id,
            //ACUERDATE DE CAMBIAR ESTO PARA CREAR PRODUCTOS PORFAVOR QUE NO SE TE OLVIDE HDP
            categoriaId =  id,//createUserCommand.categoriaId,
            name = createUserCommand.name,
            imagePath = imageName,
            description = createUserCommand.description,
            price = createUserCommand.price,
            enabled = createUserCommand.enabled,
            categoriaName = createUserCommand.categoriaName,
        )
        val element=repositorio.findByName(item.name)
        if(element!=null)
            throw IllegalArgumentException("El producto ya está registrado.")
        repositorio.add(item)
        return item.toDTO( almacenDatos.getAppDataDir()+"/productos/");
    }
}