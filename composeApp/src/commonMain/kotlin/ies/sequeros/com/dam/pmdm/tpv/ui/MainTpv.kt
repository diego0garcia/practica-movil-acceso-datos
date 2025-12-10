package ies.sequeros.com.dam.pmdm.tpv.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import ies.sequeros.com.dam.pmdm.AppViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.CategoriaViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.dependientes.DependientesViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.PedidoViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductoViewModel
import ies.sequeros.com.dam.pmdm.tpv.PrincipalTpvViewModel
import ies.sequeros.com.dam.pmdm.tpv.ui.pedido.VisualizarPedido


@Suppress("ViewModelConstructorInComposable")
@Composable
fun MainTpv(
    productoViewModel: ProductoViewModel,
    categoriaViewModel: CategoriaViewModel,
    dependientesViewModel: DependientesViewModel,
    principalTpvViewModel: PrincipalTpvViewModel,
    pedidoViewModel: PedidoViewModel,
    appViewModel: AppViewModel,
    mainViewModel: MainTpvViewModel,
    onExit: () -> Unit
) {
    //NAVEGACION
    val navController = rememberNavController()

    //OPCIONES FILTRADAS
    val options by mainViewModel.filteredItems.collectAsState() //

    val wai by appViewModel.windowsAdaptativeInfo.collectAsState();
    //SE INICIAN LAS OPCIONES DEL MENU
    mainViewModel.setOptions(
        listOf(
            ItemOption(
                Icons.Default.Home, {
                    navController.navigate(TpvRoutes.Main) {
                        launchSingleTop = true
                        popUpTo(TpvRoutes.Main)
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
                principalTpvViewModel.borrarPedido()
                onExit()
            }, "Close", false)
        )
    )

    //icono seleccionado
    //var selected by remember { mutableStateOf(items[0]) }

    val adaptiveInfo = currentWindowAdaptiveInfo()

    //NAVEGACION ENTRE PANTALLAS DEL TPV
    val navegador: @Composable () -> Unit = {
        NavHost(
            navController,
            startDestination = TpvRoutes.Main
        ) {
            composable(TpvRoutes.Main) {
                PrincipalTpv(
                    productoViewModel,
                    categoriaViewModel,
                    principalTpvViewModel,
                    onSelectItem = {
                        navController.navigate(TpvRoutes.Pedido) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(TpvRoutes.Pedido) {
                VisualizarPedido(
                    principalTpvViewModel, dependientesViewModel, pedidoViewModel,
                    onClose = {
                        navController.popBackStack()
                    }
                )
            }
        }
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
                            //si se tienen permiso
                            // if(!item.admin || (item.admin && appViewModel.hasPermission()))
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
                        // Add a fixed height constraint to prevent "Size out of range" error
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


