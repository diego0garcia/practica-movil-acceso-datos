package ies.sequeros.com.dam.pmdm.tpv.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.form.ProductoFormState
import ies.sequeros.com.dam.pmdm.tpv.PrincipalTpvViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizarPedido(
    principalTpvViewModel: PrincipalTpvViewModel,
    onClose: () -> Unit,

) {
    val items = principalTpvViewModel.pedido
    //val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .defaultMinSize(minHeight = 200.dp),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = "PEDIDO ACTUAL",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Column(
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 500.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(items.size) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp)
                                //.padding(16.dp)
                                .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                items.get(item).name + "   " + items.get(item).price + "€"
                            )
                        }
                    }
                }

            }

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(onClick = {
                    onClose()
                    principalTpvViewModel.borrarPedido() }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                }

                Button(
                    onClick = {

                    },
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                }

                FilledTonalButton(onClick = { onClose() }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }
        }
    }
}
