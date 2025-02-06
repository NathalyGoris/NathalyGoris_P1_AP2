package ucne.edu.nathalygoris_p1_ap2.presentation.sistemas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun EditSistemaScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    sistemaId: Int,
    goBack: () -> Unit
) {
    LaunchedEffect(sistemaId) {
        viewModel.selectSistema(sistemaId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EditSistemaBodyScreen(
        uiState = uiState,
        onNombresChange = viewModel::onNombreChange,
        save = viewModel::saveSistemas,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSistemaBodyScreen(
    uiState: SistemaViewModel.UiState,
    onNombresChange: (String) -> Unit,
    save: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "Editar Sistema",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF6200EE)
                ),
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                }
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
                onValueChange = onNombresChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { save() }
                ) {
                    Text(text = "Guardar")
                    Icon(Icons.Filled.Add, contentDescription = "Guardar")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { goBack() }
                ) {
                    Text(text = "Cancelar")
                    Icon(Icons.Filled.Refresh, contentDescription = "Cancelar")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            uiState.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
