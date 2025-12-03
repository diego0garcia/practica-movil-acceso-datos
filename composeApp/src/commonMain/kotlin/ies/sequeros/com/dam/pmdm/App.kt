package ies.sequeros.com.dam.pmdm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.com.dam.pmdm.administrador.AdministradorViewModel
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.BorrarDependienteUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.actualizar.ActualizarDependienteUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.crear.CrearDependienteUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.listar.ListarDependientesUseCase
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.FileDependienteRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.MemDependienteRepository
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio

import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministrador
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministradorViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.CategoriaViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.dependientes.DependientesViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.PedidoViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductoViewModel
import ies.sequeros.com.dam.pmdm.dependiente.DependienteViewModel
import ies.sequeros.com.dam.pmdm.dependiente.ui.MainDependiente
import ies.sequeros.com.dam.pmdm.dependiente.ui.MainDependienteViewModel
import ies.sequeros.com.dam.pmdm.tpv.ui.MainTpv
import ies.sequeros.com.dam.pmdm.tpv.ui.MainTpvViewModel

import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import vegaburguer.composeapp.generated.resources.Res
import vegaburguer.composeapp.generated.resources.compose_multiplatform

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
                    navController.navigate(AppRoutes.Administrador)
                },{
                    navController.navigate(AppRoutes.Dependiente)
                },{
                    navController.navigate(AppRoutes.TPV)
                })
            }
            //ADMINISTARTOR VIEW
            composable (AppRoutes.Administrador){
                MainAdministrador(appViewModel,mainViewModel,administradorViewModel,
                    dependientesViewModel,categoriasViewModel,productosViewModel,pedidosViewModel,{
                    navController.popBackStack()
                })
            }
            composable (AppRoutes.TPV){
                MainTpv(productosViewModel,categoriasViewModel,appViewModel,maintpvViewModel,
                    {
                        navController.popBackStack()
                    })
            }
            composable (AppRoutes.Dependiente){
                MainDependiente(appViewModel,mainDependienteViewModel,dependienteViewModel,
                    {
                        navController.popBackStack()
                    })
            }

        }
    }

}