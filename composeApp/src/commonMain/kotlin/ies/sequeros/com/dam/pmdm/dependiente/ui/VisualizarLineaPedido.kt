package ies.sequeros.com.dam.pmdm.dependiente.ui.lineapedido

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.LineaPedidoViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.PedidoViewModel
import ies.sequeros.com.dam.pmdm.dependiente.ui.login.FormularioLoginViewModel
import ies.sequeros.com.dam.pmdm.dependiente.ui.login.PedidoCardDependiente

@Composable
fun VisualizarLineaPedido(
    lineaPedidoViewModel: LineaPedidoViewModel,
    pedidoViewModel: PedidoViewModel,
    formularioLoginViewModel: FormularioLoginViewModel
    //onComplete: (PedidoDTO) -> Unit = {}
) {
    //val currentUser =
    val itemsPedidos by pedidoViewModel.items.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val filteredItems = itemsPedidos.filter {
        if (searchText.isNotBlank()) {
            it.id.contains(searchText, ignoreCase = true) || it.id.toString().contains(searchText, ignoreCase = true)
        } else {
            true
        }
    }

    val filteredPedidos = filteredItems.filter { it.enable }

    // Contenedor principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PEDIDO",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Barra de búsqueda
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Buscar pedido por ID...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
        }

        // Grid de líneas de pedido
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 512.dp)
        ) {
            items(filteredPedidos.size) { index ->
                PedidoCardDependiente(
                    item = filteredPedidos[index],
                    onDeactivate = {
                        val element = it.copy(
                            enable = !it.enable
                        )
                        pedidoViewModel.deliverPedido(element, formularioLoginViewModel.uiState.value.id)
                    },
                    lineaPedidoViewModel = lineaPedidoViewModel,
                    pedidoViewModel = pedidoViewModel
                )
            }
        }
    }
}