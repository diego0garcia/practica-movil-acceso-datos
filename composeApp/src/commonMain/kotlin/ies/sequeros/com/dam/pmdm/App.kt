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

import ies.sequeros.com.dam.pmdm.dependiente.DependienteViewModel
import ies.sequeros.com.dam.pmdm.dependiente.ui.MainDependiente
import ies.sequeros.com.dam.pmdm.dependiente.ui.MainDependienteViewModel
import ies.sequeros.com.dam.pmdm.tpv.ui.MainTpvViewModel
import ies.sequeros.com.dam.pmdm.dependiente.ui.LoginValidator
import ies.sequeros.com.dam.pmdm.tpv.PrincipalTpvViewModel

@Suppress("ViewModelConstructorInComposable")
@Composable
fun App(
    dependienteRepositorio : IDependienteRepositorio,
    categroiaRepositorio : ICategoriaRepositorio,
    productoRepostorio : IProductoRepositorio,
    pedidoRepositorio : IPedidoRepositorio,
    almacenImagenes:AlmacenDatos
) {


    // VIEW MODELS
    val appViewModel = viewModel { AppViewModel() }
    val mainViewModel = remember { MainAdministradorViewModel() }
    val administradorViewModel = viewModel { AdministradorViewModel() }
    val dependientesViewModel = viewModel{ DependientesViewModel(dependienteRepositorio, almacenImagenes) }
    val categoriasViewModel = viewModel{ CategoriaViewModel(categroiaRepositorio, almacenImagenes) }
    val pedidosViewModel = viewModel{ PedidoViewModel(pedidoRepositorio, almacenImagenes) }
    val productosViewModel = viewModel{ ProductoViewModel(productoRepostorio, categroiaRepositorio, almacenImagenes) }
    val principalTpvViewModel = viewModel{ PrincipalTpvViewModel()}
    appViewModel.setWindowsAdatativeInfo(currentWindowAdaptiveInfo())
    val navController = rememberNavController()

    val dependienteViewModel = viewModel { DependienteViewModel() }
    val maintpvViewModel = viewModel { MainTpvViewModel() }
    val mainDependienteViewModel = viewModel { MainDependienteViewModel() }
    //Validacion del login
    val loginValidator = remember { LoginValidator(dependienteRepositorio) }

    // UI
    AppTheme(appViewModel.darkMode.collectAsState()) {
        NavHost(
            navController,
            startDestination = AppRoutes.Main
        ) {
            // MAIN VIEW
            composable(AppRoutes.Main) {
                Principal(
                    { navController.navigate(AppRoutes.AdministradorLogin) },
                    { navController.navigate(AppRoutes.Dependiente) },
                    { navController.navigate(AppRoutes.TPV) }
                )
            }

            // LOGIN ADMIN
            composable(AppRoutes.AdministradorLogin) {
                val loginViewModel = viewModel { ies.sequeros.com.dam.pmdm.dependiente.ui.FormularioLoginViewModel() }

                ies.sequeros.com.dam.pmdm.dependiente.ui.FormularioLogin(
                    viewModel = loginViewModel,
                    onNavigateToHome = {
                        navController.navigate(AppRoutes.Administrador) {
                            launchSingleTop = true
                        }
                    },
                    validator = { nombre, contraseña ->
                        // Login de admins
                        loginValidator.validar(nombre, contraseña, soloAdmins = true)
                    }
                )
            }

            // ADMIN VIEW
            composable(AppRoutes.Administrador) {
                MainAdministrador(appViewModel, mainViewModel, administradorViewModel,dependientesViewModel,
                    categoriasViewModel,
                    productosViewModel,
                    pedidosViewModel
                ) {
                    navController.navigate(AppRoutes.Main) {
                        popUpTo(AppRoutes.Main)
                        launchSingleTop = true
                    }
                }
            }

            // TPV VIEW
            composable(AppRoutes.TPV) {
                MainTpv(productosViewModel, categoriasViewModel, principalTpvViewModel,pedidosViewModel,appViewModel, maintpvViewModel) {
                    navController.navigate(AppRoutes.Main) {
                        popUpTo(AppRoutes.Main)
                        launchSingleTop = true
                    }
                }
            }

            // LOGIN DEPENDIENTE
            composable(AppRoutes.Dependiente) {
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
                        //Login de dependiente
                        loginValidator.validar(nombre, contraseña)
                    }
                )
            }
        }
    }
}