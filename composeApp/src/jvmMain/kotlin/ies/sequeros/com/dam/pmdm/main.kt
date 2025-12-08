package ies.sequeros.com.dam.pmdm

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.BBDDCategoriaRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.BBDDDependienteRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.BBDDPedidoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.BBDDProductoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias.BBDDRepositorioCategoriaJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes.BBDDRepositorioDependientesJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedidos.BBDDRepositorioPedidoJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.lineapedido.BBDDRepositorioLineaPedidoJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.productos.BBDDRepositorioProductoJava
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.BBDDLineaPedidoRepository
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import java.io.FileInputStream
import java.util.logging.LogManager
fun main() = application {
    val dependienteRepositorioJava=BBDDRepositorioDependientesJava("/app.properties")
    val categoriaRepositorioJava= BBDDRepositorioCategoriaJava("/app.properties")
    val productoRepositorioJava= BBDDRepositorioProductoJava("/app.properties")
    val pedidoRepositorioJava= BBDDRepositorioPedidoJava("/app.properties")
    val lineaPedidoRepositorioJava= BBDDRepositorioLineaPedidoJava("/app.properties")

    val dependienteRepositorio: IDependienteRepositorio = BBDDDependienteRepository(dependienteRepositorioJava )
    val categoriaRepositorio: ICategoriaRepositorio = BBDDCategoriaRepository(categoriaRepositorioJava)
    val productoRepositorio: IProductoRepositorio = BBDDProductoRepository(productoRepositorioJava)
    val pedidoRepositorio: IPedidoRepositorio = BBDDPedidoRepository(pedidoRepositorioJava)
    val lineaPedidoRepositorio: ILineaPedidoRepositorio = BBDDLineaPedidoRepository(lineaPedidoRepositorioJava)

    configureExternalLogging("./logging.properties")
    Window(
        onCloseRequest = {
            //se cierra la conexion
            dependienteRepositorioJava.close()
            exitApplication()},
        title = "VegaBurguer",
    ) {
        //se envuelve el repositorio en java en uno que exista en Kotlin
        App(dependienteRepositorio,categoriaRepositorio, productoRepositorio,pedidoRepositorio,lineaPedidoRepositorio,AlmacenDatos())
    }
}
fun configureExternalLogging(path: String) {
    try {
        FileInputStream(path).use { fis ->
            LogManager.getLogManager().readConfiguration(fis)
            println("Logging configurado desde: $path")
        }
    } catch (e: Exception) {
        println("⚠️ No se pudo cargar logging.properties externo: $path")
        e.printStackTrace()
    }
}