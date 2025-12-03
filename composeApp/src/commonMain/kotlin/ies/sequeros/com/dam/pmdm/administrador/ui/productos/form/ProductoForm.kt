package ies.sequeros.com.dam.pmdm.administrador.ui.productos.form

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductoViewModel
import ies.sequeros.com.dam.pmdm.commons.ui.ImagenDesdePath
import ies.sequeros.com.dam.pmdm.commons.ui.SelectorImagenComposable
import vegaburguer.composeapp.generated.resources.Res
import vegaburguer.composeapp.generated.resources.hombre

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoForm(
    productoViewModel: ProductoViewModel,
    onClose: () -> Unit,
    onConfirm: (datos: ProductoFormState) -> Unit = {},
    productoFormularioViewModel: ProductoFormViewModel = viewModel {
        ProductoFormViewModel(
            productoViewModel.almacenDatos,
            productoViewModel.selected.value,
            onConfirm
        )
    }
) {
    val categoria by productoViewModel.categoria.collectAsState()
    val categoriaSelected by productoViewModel.categoriaSelected.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    val state by productoFormularioViewModel.uiState.collectAsState()
    val formValid by productoFormularioViewModel.isFormValid.collectAsState()
    val selected = productoViewModel.selected.collectAsState()
    val imagePath = remember { mutableStateOf(if (!state.imagePath.isNullOrEmpty()) state.imagePath else "") }

    val scrollState = rememberScrollState()

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
                .padding(24.dp)
                .verticalScroll(scrollState),
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
                    text = if (selected.value == null) "Crear nuevo producto" else "Editar producto",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Campos de texto
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { productoFormularioViewModel.onNombreChange(it) },
                label = { Text("Nombre Del Producto") },
                leadingIcon = { Icon(Icons.Default.Fastfood, contentDescription = null) },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.nombreError?.let { Text(it, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error) }

            OutlinedTextField(
                value = state.descripcion,
                onValueChange = { productoFormularioViewModel.onDescripcionChange(it) },
                label = { Text("Descripción") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                isError = state.descripcionError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.descripcionError?.let { Text(it, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error) }

            OutlinedTextField(
                value = state.precio,
                onValueChange = { productoFormularioViewModel.onPrecioChange(it) },
                label = { Text("Precio Del Producto") },
                leadingIcon = { Icon(Icons.Default.Money, contentDescription = null) },
                isError = state.precioError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.precioError?.let { Text(it, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error) }

            Column {
                OutlinedTextField(
                    value = state.categoriaName,
                    onValueChange = {}, // se deja vacío
                    readOnly = true,
                    label = { Text("Categoría") },
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
                        categoria.forEach { cat ->
                            Text(
                                text = cat.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {

                                        //productoViewModel.setCategoriaSeleccionada(cat)
                                        productoFormularioViewModel.onCategoriaNameChange(cat.name)
                                        println(cat.id)
                                        productoFormularioViewModel.onCategoriaIdChange(cat.id)
                                        expanded = false
                                    }
                            )
                        }
                    }
                }
            }


            // Checkbox activo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = state.enabled,
                    onCheckedChange = { productoFormularioViewModel.onEnabledChange(it) }
                )
                Text("Activo", style = MaterialTheme.typography.bodyMedium)
            }

            // Selector de imagen
            Text("Selecciona una imagen del producto:", style = MaterialTheme.typography.titleSmall)
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            SelectorImagenComposable { path ->
                productoFormularioViewModel.onImagePathChange(path)
                imagePath.value = path
            }

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            ImagenDesdePath(imagePath, Res.drawable.hombre, Modifier.fillMaxSize())
            state.imagePathError?.let { Text(it, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error) }

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(onClick = { productoFormularioViewModel.clear() }) {
                    Icon(Icons.Default.Autorenew, contentDescription = null)
                }

                Button(
                    onClick = {
                        productoFormularioViewModel.submit(
                            onSuccess = { onConfirm(productoFormularioViewModel.uiState.value) },
                            onFailure = {}
                        )
                    },
                    enabled = formValid
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                }

                FilledTonalButton(onClick = { onClose() }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }
        }
    }
}
