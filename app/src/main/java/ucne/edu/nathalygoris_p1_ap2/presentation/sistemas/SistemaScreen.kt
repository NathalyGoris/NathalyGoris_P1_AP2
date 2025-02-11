package ucne.edu.nathalygoris_p1_ap2.presentation.sistemas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.ButtonDefaults



@Composable
fun SistemaScreen(viewModel: SistemasViewModel = hiltViewModel(), goBack: () -> Unit) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SistemaBodyScreen(
        uiState = uiState.value,
        onNombreChange = viewModel::onNombreChange,
        onPrecioChange = viewModel::onPrecioChange,
        save = viewModel::saveSistemas,
        nuevo = viewModel::nuevoSistema,
        goBack = goBack
    )
}

@Composable
fun SistemaBodyScreen(
    uiState: SistemasViewModel.UiState,
    onNombreChange: (String) -> Unit,
    onPrecioChange: (String) -> Unit,
    save: () -> Unit,
    nuevo: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Text(
                text = "Registro de Sistemas",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Magenta,
                    textAlign = TextAlign.Center
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Nombre") },
                value = uiState.nombre,
                onValueChange = onNombreChange
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Precio") },
                value = uiState.precio?.toString() ?: "",
                onValueChange = { input ->
                    try {
                        onPrecioChange(input.toDoubleOrNull()?.toString() ?: "")
                    } catch (e: NumberFormatException) {
                        onPrecioChange("")
                    }
                },
                isError = uiState.precio == null
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { save() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFFF00FF),
                        contentColor = Color.White,
                    )
                ) {
                    Text(text = "Guardar")
                    Icon(Icons.Default.Add, contentDescription = "Guardar")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { nuevo() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFFF00FF),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Nuevo")
                    Icon(Icons.Default.Refresh, contentDescription = "Nuevo")
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            uiState.successMessage?.let { message ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    content = {
                        Text(
                            text = message,
                            color = Color.Green,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)

                        )
                    }
                )
            }

            uiState.errorMessage?.let { message ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    content = {
                        Text(
                            text = message,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                )
            }
        }
    }
}










