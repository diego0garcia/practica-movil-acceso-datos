package ies.sequeros.com.dam.pmdm.administrador.ui.categorias.form

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
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.CategoriaViewModel
import ies.sequeros.com.dam.pmdm.commons.ui.ImagenDesdePath
import ies.sequeros.com.dam.pmdm.commons.ui.SelectorImagenComposable
import vegaburguer.composeapp.generated.resources.Res
import vegaburguer.composeapp.generated.resources.hombre


@Composable
fun CategoriaForm(
    //appViewModel: AppViewModel,
    categoriaViewModel: CategoriaViewModel,
    onClose: () -> Unit,
    onConfirm: (datos: CategoriaFormState) -> Unit = {},
    categoriaFormularioViewModel: CategoriaFormViewModel = viewModel {
        CategoriaFormViewModel(
            categoriaViewModel.selected.value, onConfirm
        )
    }
) {
    val state by categoriaFormularioViewModel.uiState.collectAsState()
    val formValid by categoriaFormularioViewModel.isFormValid.collectAsState()
    val selected = categoriaViewModel.selected.collectAsState()
    val imagePath = remember { mutableStateOf(if (state.imagePath != null && state.imagePath.isNotEmpty()) state.imagePath else "") }

    //val names = CartoonString.getNames() - "default"

    //  Scroll state
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
                .verticalScroll(scrollState), // 👈 Aquí el scroll vertical
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //  Título
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Icecream,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = if (selected == null)
                        "Crear nueva categoría"
                    else
                        "Editar categoría",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(8.dp)) // Espacio antes del botón

            //Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            //  Campos
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { categoriaFormularioViewModel.onNombreChange(it) },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Default.PersonOutline, contentDescription = null) },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.nombreError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            //Descripción
            OutlinedTextField(
                value = state.descripcion,
                onValueChange = { categoriaFormularioViewModel.onDescripcionChange(it) },
                label = { Text("Descripción de la categoría") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                isError = state.descripcionError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.descripcionError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            // Checkboxes
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = state.enabled,
                        onCheckedChange = { categoriaFormularioViewModel.onEnabledChange(it) }
                    )
                    Text("Activo", style = MaterialTheme.typography.bodyMedium)
                }
            }

            //  Selector de avatar
            Text("Selecciona un avatar:", style = MaterialTheme.typography.titleSmall)

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
            val scope = rememberCoroutineScope()
            SelectorImagenComposable({ it: String ->
                categoriaFormularioViewModel.onImagePathChange(it)//  dependienteViewModel.almacenDatos.copy(it, "prueba","/dependientes_imgs/")
                imagePath.value = it
            })

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            ImagenDesdePath(imagePath, Res.drawable.hombre, Modifier.fillMaxSize())
            state.imagePathError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            //  Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(onClick = { categoriaFormularioViewModel.clear() }) {
                    Icon(Icons.Default.Autorenew, contentDescription = null)
                    //Spacer(Modifier.width(6.dp))
                    //Text("Limpiar")
                }

                Button(
                    onClick = {
                        categoriaFormularioViewModel.submit(
                            onSuccess = {
                                onConfirm(categoriaFormularioViewModel.uiState.value)
                            },
                            onFailure = {}
                        )
                    },
                    enabled = formValid
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                   // Spacer(Modifier.width(6.dp))
                    //Text("" + formValid.toString())
                }

                FilledTonalButton(onClick = { onClose() }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    //Spacer(Modifier.width(6.dp))
                    //Text("Cancelar")
                }
            }
        }
    }
}






