package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.actualizar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.actualizar.ActualizarDependienteCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.Dependiente
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ActualizarCategoriaUseCase(private val repositorio: ICategoriaRepositorio,
                                   private val almacenDatos: AlmacenDatos) {

    suspend fun invoke(command: ActualizarCategoriaCommand, ): CategoriaDTO {

        val item: Categoria?=repositorio.getById(command.id)

        //val nombreArchivo = command.imagePath.substringAfterLast('/')
        var nuevaImagePath:String?=null
        if (item==null) {
            throw IllegalArgumentException("El usuario no esta registrado.")
        }
        //se pasa a dto para tener el path
        var itemDTO: CategoriaDTO=item.toDTO(almacenDatos.getAppDataDir()+"/categorias/")

        //si las rutas son diferentes se borra y se copia
        if(itemDTO.imagePath!=command.imagePath) {
            almacenDatos.remove(itemDTO.imagePath)
            nuevaImagePath=almacenDatos.copy(command.imagePath,command.id,"/categorias/")
        }else{
            nuevaImagePath=item.imagePath
        }

        var newCategoria= item.copy(
            id=command.id,
            name=command.name,
            //si se ha sustituido
            imagePath = nuevaImagePath,
            descripcion =command.descripcion,
            enabled = command.enabled
        )
        repositorio.update(newCategoria)
        //se devuelve con el path correcto
        return newCategoria.toDTO(almacenDatos.getAppDataDir()+"/categorias/")
    }
}