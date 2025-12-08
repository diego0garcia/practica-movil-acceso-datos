package ies.sequeros.com.dam.pmdm.dependiente.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import ies.sequeros.com.dam.pmdm.dependiente.ui.LineaPedidoViewModel
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio


@Composable
fun PrincipalDependiente(pedidoRepositorio: IPedidoRepositorio, lineaPedidoViewModel: LineaPedidoViewModel) {
    Scaffold(
        topBar = {},
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                // Mostramos únicamente nombre del producto, precio
                val pedidos = remember { mutableStateListOf<Pedido>() }
                LaunchedEffect(Unit) {
                    pedidos.addAll(pedidoRepositorio.getAll())
                }

                if (pedidos.isNotEmpty()) {
                    // Mostramos los pedidos en filas 
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        pedidos.chunked(2).forEach { rowPedidos ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                rowPedidos.forEach { p ->
                                    Surface(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(8.dp)
                                            .fillMaxWidth()
                                            .defaultMinSize(minHeight = 160.dp),
                                        tonalElevation = 4.dp,
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.surface
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(24.dp),
                                            verticalArrangement = Arrangement.spacedBy(12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            // título del pedido (id)
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = p.id,
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }

                                            val lineas = remember(p.id) { mutableStateListOf<LineaPedido>() }
                                            LaunchedEffect(p.id) {
                                                // Cargamos las líneas del pedido
                                                lineas.clear()
                                                lineas.addAll(lineaPedidoViewModel.findByPedido(p.id))
                                            }

                                            // Mostramos las líneas del pedido
                                            if (lineas.isNotEmpty()) {
                                                lineas.forEachIndexed { index, linea ->
                                                    // Mostramos el nombre y precio del producto
                                                    Column(
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        Text(
                                                            text = linea.product_name,
                                                            style = MaterialTheme.typography.titleSmall,
                                                            color = MaterialTheme.colorScheme.onSurface
                                                        )
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        Text(
                                                            text = "€ ${linea.product_price}",
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            color = MaterialTheme.colorScheme.primary
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.height(12.dp))
                                                }
                                            } else {
                                                // Si no hay líneas, mostramos un mensaje
                                                Text(
                                                    text = "Sin productos",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    }
                                }

                                // Si la fila tiene un elemento, añadimos un spacer para equilibrar
                                if (rowPedidos.size < 2) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                } else {
                    Text(text = "No hay pedidos", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }

            }
        }
    }
}

@Composable
private fun ActionIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, description: String) {
    Surface(
        shape = CircleShape,
        tonalElevation = 2.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.size(56.dp)
    ) {
        IconButton(onClick = { /* acción */ }, modifier = Modifier.fillMaxSize()) {
            Icon(icon, contentDescription = description, tint = MaterialTheme.colorScheme.onSurface)
        }
    }
}
