package ies.sequeros.com.dam.pmdm.dependiente.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold


import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import ies.sequeros.com.dam.pmdm.AppViewModel
import ies.sequeros.com.dam.pmdm.dependiente.DependienteViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.PedidoViewModel


@Suppress("ViewModelConstructorInComposable")
@Composable
fun MainDependiente(
    appViewModel: AppViewModel,
    mainViewModel: MainDependienteViewModel,
    dependienteViewModel: DependienteViewModel,
    pedidoViewModel: PedidoViewModel? = null,
    onExit: () -> Unit,
    validator: (suspend (String, String) -> String)? = null
) {

    val navController = rememberNavController()
    val options by mainViewModel.filteredItems.collectAsState() //

    val wai by appViewModel.windowsAdaptativeInfo.collectAsState();
    mainViewModel.setOptions(
        listOf(
            ItemOption(
                Icons.Default.Home, {
                    navController.navigate(DependienteRoutes.Main) {
                        launchSingleTop = true
                        popUpTo(DependienteRoutes.Main)
                    }
                },
                "Home", false
            ),

            ItemOption(
                Icons.Default.DarkMode,
                {
                    appViewModel.swithMode()
                },
                "Darkmode",
                true
            ),

            ItemOption(Icons.Default.Close, {
                onExit()
            }, "Close", false)
        )
    )

    //icono seleccionado
    //var selected by remember { mutableStateOf(items[0]) }

    val adaptiveInfo = currentWindowAdaptiveInfo()


    val loginViewModel = viewModel { FormularioLoginViewModel() }

    val navegador: @Composable () -> Unit = {
        NavHost(
            navController,
            startDestination = DependienteRoutes.Login
        ) {
            //Para que se inicie el Login
            composable(DependienteRoutes.Login) {
                FormularioLogin(
                    viewModel = loginViewModel,
                    onNavigateToHome = {
                        navController.navigate(DependienteRoutes.Main)
                    },
                    validator = validator
                )
            }
            composable(DependienteRoutes.Main) {
                PrincipalDependiente(pedidoViewModel = pedidoViewModel)
            }
        }
    }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    //Si estas en login no abre la pantalla principal
    if (currentRoute == DependienteRoutes.Login) {
        navegador()
        return
    }



    if (wai?.windowSizeClass?.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
        Scaffold(
            bottomBar = {

                NavigationBar {
                    mainViewModel.filteredItems.collectAsState().value.forEach { item ->
                        // if(!item.admin || (item.admin && appViewModel.hasPermission()))
                        NavigationBarItem(

                            selected = true,
                            onClick = { item.action() },

                            icon = { Icon(item.icon, contentDescription = item.name) },

                            )
                    }
                }
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                navegador()
            }
        }
    } else {

        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    Modifier.then(
                        if (wai?.windowSizeClass?.windowWidthSizeClass == WindowWidthSizeClass.COMPACT)
                            Modifier.width(128.dp)
                        else Modifier.width(128.dp)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight()  // ocupa todo el alto del drawer
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Center,  // centra verticalmente
                        horizontalAlignment = Alignment.CenterHorizontally  // opcional: centra horizontalmente
                    ) {

                        Spacer(Modifier.height(16.dp))
                        options.forEach { item ->
                            NavigationDrawerItem(
                                icon = {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center,

                                        ) {
                                        Icon(
                                            item.icon,
                                            tint = MaterialTheme.colorScheme.primary,
                                            contentDescription = item.name
                                        )
                                    }
                                },
                                label = { appViewModel.windowsAdaptativeInfo.collectAsState().value?.windowSizeClass.toString() }, // sin texto
                                selected = false,
                                onClick = { item.action() },
                                modifier = Modifier
                                    .padding(vertical = 4.dp) // espaciado entre items

                            )
                        }
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .height(600.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    navegador()

                }
            }
        )
    }


}


