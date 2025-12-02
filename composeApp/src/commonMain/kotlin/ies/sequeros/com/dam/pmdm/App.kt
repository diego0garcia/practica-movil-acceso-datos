package ies.sequeros.com.dam.pmdm

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.com.dam.pmdm.administrador.AdministradorViewModel
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio

import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministrador
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministradorViewModel
import ies.sequeros.com.dam.pmdm.tpv.ui.MainTpv
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.CategoriaViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.dependientes.DependientesViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.PedidoViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductoViewModel

import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import vegaburguer.composeapp.generated.resources.Res
import vegaburguer.composeapp.generated.resources.compose_multiplatform
import ies.sequeros.com.dam.pmdm.dependiente.DependienteViewModel
import ies.sequeros.com.dam.pmdm.dependiente.ui.MainDependiente
import ies.sequeros.com.dam.pmdm.dependiente.ui.MainDependienteViewModel
import ies.sequeros.com.dam.pmdm.tpv.ui.MainTpvViewModel

@Suppress("ViewModelConstructorInComposable")
@Composable
fun App( dependienteRepositorio : IDependienteRepositorio, categroiaRepositorio : ICategoriaRepositorio, productoRepostorio : IProductoRepositorio, pedidoRepositorio : IPedidoRepositorio, almacenImagenes:AlmacenDatos) {

    //VIEW MODELS
    val appViewModel = viewModel {  AppViewModel() }
    val mainViewModel = remember { MainAdministradorViewModel() }
    val administradorViewModel = viewModel { AdministradorViewModel() }
    val dependientesViewModel = viewModel{ DependientesViewModel(
        dependienteRepositorio, almacenImagenes
    )}
    val categoriasViewModel = viewModel{ CategoriaViewModel(
        categroiaRepositorio, almacenImagenes
    )}
    val pedidosViewModel = viewModel{ PedidoViewModel(
        pedidoRepositorio, almacenImagenes
    )}
    val productosViewModel = viewModel{ ProductoViewModel(
        productoRepostorio, categroiaRepositorio,almacenImagenes
    )}

    appViewModel.setWindowsAdatativeInfo( currentWindowAdaptiveInfo())
    val navController= rememberNavController()

    val dependienteViewModel = viewModel { DependienteViewModel() }
    val maintpvViewModel = viewModel { MainTpvViewModel() }
    val mainDependienteViewModel = viewModel { MainDependienteViewModel() }

    //LAUNCH UI
    AppTheme(appViewModel.darkMode.collectAsState()) { //Load the app theme
        NavHost(
            navController,
            startDestination = AppRoutes.Main
        ) {
            //MAIN VIEW
            composable(AppRoutes.Main) {
                Principal({
                    // abrir login de administrador reutilizando el mismo formulario
                    navController.navigate(AppRoutes.AdministradorLogin)
                },{navController.navigate(AppRoutes.Dependiente)},{ navController.navigate(AppRoutes.TPV)})
            }
            // ADMIN LOGIN: reutiliza el formulario de dependiente pero valida contra el repositorio de dependientes
            composable(AppRoutes.AdministradorLogin) {
                // creamos un viewModel local para el formulario
                val loginViewModel = viewModel { ies.sequeros.com.dam.pmdm.dependiente.ui.FormularioLoginViewModel() }
                ies.sequeros.com.dam.pmdm.dependiente.ui.FormularioLogin(
                    viewModel = loginViewModel,
                    onNavigateToHome = {
                        // al validar correctamente, navegamos a la vista de administrador
                        navController.navigate(AppRoutes.Administrador) {
                            launchSingleTop = true
                        }
                    },
                    validator = { nombre, contraseña ->
                        // validator: busca en repositorio por name o email y comprueba contraseña
                        val items = dependienteRepositorio.getAll()
                        val user = items.firstOrNull { it.name.equals(nombre, true) || it.email.equals(nombre, true) }
                        if (user == null) return@FormularioLogin "Usuario o contraseña incorrectos"
                        if (!user.enabled) return@FormularioLogin "Usuario deshabilitado"
                        if (user.password != contraseña) return@FormularioLogin "Usuario o contraseña incorrectos"
                        if (!user.isAdmin) return@FormularioLogin "Este usuario no es administrador"
                        "" // OK
                    }
                )
            }

            //ADMINISTARTOR VIEW
            composable (AppRoutes.Administrador){
                MainAdministrador(appViewModel,mainViewModel,administradorViewModel,
                    dependientesViewModel,categoriasViewModel,productosViewModel,pedidosViewModel,{
                    // regresar al inicio
                    navController.navigate(AppRoutes.Main) {
                        // limpia backstack para evitar volver al login accidentalmente
                        popUpTo(AppRoutes.Main)
                        launchSingleTop = true
                    }
                })
            }
            composable (AppRoutes.TPV){
                MainTpv(appViewModel,maintpvViewModel,
                    {
                        navController.navigate(AppRoutes.Main) {
                            popUpTo(AppRoutes.Main)
                            launchSingleTop = true
                        }
                    })
            }
            composable (AppRoutes.Dependiente){
                MainDependiente(
                    appViewModel,
                    mainDependienteViewModel,
                    dependienteViewModel,
                    {
                        navController.navigate(AppRoutes.Main) {
                            popUpTo(AppRoutes.Main)
                            launchSingleTop = true
                        }
                    },
                    validator = { nombre, contraseña ->
                        val items = dependienteRepositorio.getAll()
                        val user = items.firstOrNull { it.name.equals(nombre, true) || it.email.equals(nombre, true) }
                        if (user == null) return@MainDependiente "Usuario o contraseña incorrectos"
                        if (!user.enabled) return@MainDependiente "Usuario deshabilitado"
                        if (user.password != contraseña) return@MainDependiente "Usuario o contraseña incorrectos"
                        ""
                    }
                )
            }


        }
    }

}