package ies.sequeros.com.dam.pmdm.tpv.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.PersonPinCircle

import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TableBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ies.sequeros.com.dam.pmdm.AppTheme
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.DependienteDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.CategoriaViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductoCard
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductoViewModel
import ies.sequeros.com.dam.pmdm.tpv.PrincipalTpvViewModel
import java.security.Principal


@Composable
fun PrincipalTpv(
    productoViewModel: ProductoViewModel,
    categoriaViewModel: CategoriaViewModel,
    principalTpvViewModel: PrincipalTpvViewModel,
    onSelectItem: () -> Unit
    ) {
    val categorias by categoriaViewModel.items.collectAsState()
    val productos by productoViewModel.items.collectAsState()
    val pedido = principalTpvViewModel.pedido

    var searchText by remember { mutableStateOf("") }

    val filteredCategorias = categorias.filter { it.enabled == true}

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    //.padding(16.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.CenterEnd,
            ) {
                //BOTÓN DEL CARRITO
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    onClick = {
                        onSelectItem()
                    }
                ){
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        modifier = Modifier.size(40.dp)
                    )
                    if (pedido.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.Red, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = pedido.size.toString(),
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            //contentAlignment = Alignment.Center
        ) {
            Column(
                //horizontalAlignment = Alignment.TopCenter,
                verticalArrangement = Arrangement.Top
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(
                        minSize = 512.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    items(filteredCategorias.size) { item ->
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp))
                        {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(42.dp)
                                    //.padding(16.dp)
                                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    filteredCategorias.get(item).name
                                )
                            }
                            // FILTRAR PRODUCTO POR CATEGORIAS USANDO ID
                            val productosFiltrados = productos.filter { it.categoriaId == filteredCategorias.get(item).id && it.enabled == true}
                            if(productosFiltrados.isEmpty()){
                                Box(
                                    Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = "Categoría vacía",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }else{
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(3),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 500.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    items(productosFiltrados.size) { item ->
                                        Button(
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Transparent,
                                                contentColor = Color.Unspecified
                                            ),
                                            shape = RectangleShape,
                                            elevation = null,
                                            contentPadding = PaddingValues(0.dp),
                                            onClick = {
                                                principalTpvViewModel.añadirProducto(productosFiltrados.get(item))
                                            }
                                        ) {
                                            ProductoCardTPV(
                                                productosFiltrados.get(item)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
