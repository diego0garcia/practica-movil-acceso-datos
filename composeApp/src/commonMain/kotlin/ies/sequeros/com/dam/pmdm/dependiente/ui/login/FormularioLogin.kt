package ies.sequeros.com.dam.pmdm.dependiente.ui.login


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
@Composable
fun FormularioLogin(
    viewModel: FormularioLoginViewModel,
    onNavigateToHome: () -> Unit,
    validator: (suspend (String, String) -> String)? = null
) {

    val state by viewModel.uiState.collectAsState()
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFAD46FF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(350.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(36.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text="INICIAR SESIÓN", fontWeight = FontWeight.Bold, fontSize = 32.sp, fontFamily = FontFamily.Monospace)

            TextField(
                value = state.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.contraseña,
                onValueChange = { viewModel.onContraseñaChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                errorMessage
            )

            Button(
                onClick = {
                    if (validator != null) {
                        scope.launch {
                            val error = validator(state.nombre, state.contraseña)
                            errorMessage = error

                            if (error.isEmpty()) {
                                onNavigateToHome()
                            }
                        }
                    }
                },
                enabled = state.nombre.isNotEmpty() && state.contraseña.isNotEmpty()
            ) {
                Text("Aceptar")
            }

        }
    }
}
