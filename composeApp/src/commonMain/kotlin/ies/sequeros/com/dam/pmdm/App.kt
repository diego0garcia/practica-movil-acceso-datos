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

import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministrador
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministradorViewModel
import ies.sequeros.com.dam.pmdm.tpv.ui.MainTpv
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.CategoriaViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.dependientes.DependientesViewModel
import ies.sequeros.com.dam.pmdm.dependiente.DependienteViewModel
import ies.sequeros.com.dam.pmdm.dependiente.ui.MainDependiente
import ies.sequeros.com.dam.pmdm.dependiente.ui.MainDependienteViewModel
import ies.sequeros.com.dam.pmdm.tpv.ui.MainTpvViewModel

@Suppress("ViewModelConstructorInComposable")
@Composable
fun App( dependienteRepositorio : IDependienteRepositorio,categroiaRepositorio : ICategoriaRepositorio, almacenImagenes:AlmacenDatos) {

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
                },{navController.navigate(AppRoutes.Dependiente)},{ navController.navigate(AppRoutes.TPV)})
            }
            //ADMINISTARTOR VIEW
            composable (AppRoutes.Administrador){
                MainAdministrador(appViewModel,mainViewModel,administradorViewModel,
                    dependientesViewModel,categoriasViewModel,{
                    navController.popBackStack()
                })
            }
            composable (AppRoutes.TPV){
                MainTpv(appViewModel,maintpvViewModel,
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