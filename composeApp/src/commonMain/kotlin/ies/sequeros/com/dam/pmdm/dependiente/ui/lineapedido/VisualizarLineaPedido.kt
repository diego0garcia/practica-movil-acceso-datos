package ies.sequeros.com.dam.pmdm.dependiente.ui.lineapedido

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.LineaPedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.lineapedido.LineaPedidoViewModel

@Composable
fun VisualizarLineaPedido(
    lineaPedidoViewModel: LineaPedidoViewModel,
    onDelete: (LineaPedidoDTO) -> Unit = {}
) {
    val items by lineaPedidoViewModel.items.collectAsState()
    var searchText by remember { mutableStateOf("") }

    val filteredItems = items.filter {
        if (searchText.isNotBlank()) {
            it.product_name.contains(searchText, ignoreCase = true) ||
                    it.id.toString().contains(searchText, ignoreCase = true)
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
            text = "LÍNEAS DE PEDIDO",
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
                placeholder = { Text("Buscar por producto o ID...") },
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
            items(filteredItems.size) { index ->
                LineaPedidoCard(
                    item = filteredItems[index],
                    onDelete = onDelete,
                )
            }
        }
    }
}