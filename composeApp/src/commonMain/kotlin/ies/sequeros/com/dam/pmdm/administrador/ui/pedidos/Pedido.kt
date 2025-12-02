package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.PedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministradorViewModel

@Composable
fun Pedidos (
    mainAdministradorViewModel: MainAdministradorViewModel,
    pedidoViewModel: PedidoViewModel,
    onSelectItem:(PedidoDTO?)->Unit
){
    val items by pedidoViewModel.items.collectAsState()
    var searchText by remember { mutableStateOf("") }

    val filteredItems = items.filter {
        if (searchText.isNotBlank()) {
            it.name.contains(searchText, ignoreCase = true) || it.name.contains(searchText, ignoreCase = true)
        } else {
            true
        }
    }

    // Contenedor principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PEDIDOS",
            color = MaterialTheme.colorScheme.primary
        )
        // 🔹 Barra superior fija con buscador y botón añadir
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Buscar...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            Spacer(Modifier.width(8.dp))
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(
                minSize = 512.dp
            )
        ) {

            items(filteredItems.size) { item ->
                PedidoCard(
                    filteredItems.get(item),
                    {

                        val element = it.copy(
                            enable = !it.enable
                        )
                        pedidoViewModel.switchEnablePedido(element)
                    }, {

                        val element = it.copy(
                            enable = !it.enable
                        )
                        pedidoViewModel.switchEnablePedido(element)
                    }, {}, {
                        onSelectItem(it);

                    }, {
                        pedidoViewModel.delete(it)
                    })
            }
        }
    }
}
