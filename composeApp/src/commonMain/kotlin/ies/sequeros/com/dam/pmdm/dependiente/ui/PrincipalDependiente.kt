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

                // Mostramos únicamente nombre del producto, precio y fecha

                // Cargamos pedidos
                val pedidosState = produceState<List<Pedido>>(initialValue = emptyList()) {
                    value = pedidoRepositorio.getAll()
                }
                val pedidos = pedidosState.value

                if (pedidos.isNotEmpty()) {
                    // Mostramos los pedidos en filas de 2 tarjetas por fila
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
                                        shape = RoundedCornerShape(12.dp),
                                        tonalElevation = 2.dp,
                                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(8.dp)
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.padding(24.dp)
                                        ) {
                                            // título del pedido (id)
                                            Text(text = "Pedido: ${p.id}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                            Spacer(modifier = Modifier.height(8.dp))

                                            val lineasState = produceState<List<LineaPedido>>(initialValue = emptyList(), key1 = p.id) {
                                                value = lineaPedidoViewModel.findByPedido(p.id)
                                            }
                                            val lineas = lineasState.value

                                            if (lineas.isNotEmpty()) {
                                                lineas.forEachIndexed { index, linea ->
                                                    Text(text = linea.product_name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                                                    Text(text = "Precio: ${linea.product_price}", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
                                                    if (index < lineas.lastIndex) {
                                                        Spacer(modifier = Modifier.height(8.dp))
                                                        Divider(modifier = Modifier.fillMaxWidth(0.6f))
                                                        Spacer(modifier = Modifier.height(8.dp))
                                                    }
                                                }
                                            } else {
                                                Text(text = "--", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                                                Text(text = "Precio: --", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
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
