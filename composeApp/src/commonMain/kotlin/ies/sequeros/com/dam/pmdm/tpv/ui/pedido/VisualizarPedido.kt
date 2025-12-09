package ies.sequeros.com.dam.pmdm.tpv.ui.pedido

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fastfood
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
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.crear.CrearLineaPedidoCommand
import ies.sequeros.com.dam.pmdm.administrador.ui.dependientes.DependientesViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.PedidoViewModel
import ies.sequeros.com.dam.pmdm.dependiente.DependienteViewModel
import ies.sequeros.com.dam.pmdm.generateUUID
import ies.sequeros.com.dam.pmdm.tpv.PrincipalTpvViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizarPedido(
    principalTpvViewModel: PrincipalTpvViewModel,
    dependientesViewModel: DependientesViewModel,
    pedidoViewModel: PedidoViewModel,
    onClose: () -> Unit,
) {
    val dependientes by dependientesViewModel.items.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val items = principalTpvViewModel.pedido
    val totalPrice = items.sumOf { it.price.toDouble() }.toFloat()

    fun terminarPedido() {
        val pedidoID = generateUUID()

        if (items.isNotEmpty()) {
            principalTpvViewModel.add(pedidoID)
            onClose()
            principalTpvViewModel.borrarPedido()
        } else print("PEDIDO VACIO")
    }
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
                //Seleccionar dependientes
                Column {
                    OutlinedTextField(
                        value = principalTpvViewModel.dependienteSelected,
                        onValueChange = {}, // se deja vacío
                        readOnly = true,
                        label = { Text("Dependiente") },
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )

                    if (expanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp)
                                .border(1.dp, MaterialTheme.colorScheme.outline)
                        ) {
                            dependientes.forEach { dep ->
                                Text(
                                    text = dep.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .clickable {
                                            principalTpvViewModel.dependienteSelected = dep.id
                                            principalTpvViewModel.actualizarDependiente(dep.id)
                                            expanded = false
                                        }
                                )
                            }
                        }
                    }
                }
            }

            Column() {
                if (items.isEmpty()) {
                    Text("El carrito está vacío, compra algo anda.")
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 500.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        items(items.size) { item ->
                            //totalPrice += items.get(item).price.toFloat()
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(42.dp)
                                    //.padding(16.dp)
                                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    items.get(item).name + "   " + items.get(item).price + "€"
                                )
                                IconButton(
                                    onClick = {
                                        principalTpvViewModel.borrarLinea(item)
                                    }
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }

                            }
                        }
                    }
                    HorizontalDivider(
                        Modifier.fillMaxWidth(0.8f).padding(10.dp),
                        DividerDefaults.Thickness, MaterialTheme.colorScheme.outlineVariant
                    )
                    Text(
                        text = "Precio total: " + totalPrice.toString() + "€",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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
                    principalTpvViewModel.borrarPedido()
                }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                }

                Button(
                    onClick = {
                        terminarPedido()
                    },
                    enabled = items.isNotEmpty() && principalTpvViewModel.dependienteSelected.isNotBlank()
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
