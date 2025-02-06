package ucne.edu.nathalygoris_p1_ap2.presentation.sistemas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ucne.edu.nathalygoris_p1_ap2.data.local.entities.SistemaEntity

@Composable
fun SystemListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: SistemaViewModel = hiltViewModel(),
    addSystem: () -> Unit,
    editSystem: (Int) -> Unit,
    deleteSystem: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SystemListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        addSystem = addSystem,
        editSystem = editSystem,
        deleteSystem = deleteSystem
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: SistemaViewModel.UiState,
    addSystem: () -> Unit,
    editSystem: (Int) -> Unit,
    deleteSystem: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Available Systems",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open menu")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1976D2)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = addSystem,
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "New System")
            }
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.sistemas) { sistema ->
                    SystemRow(sistema, editSystem, deleteSystem)
                }
            }
        }
    }
}

@Composable
fun SystemRow(
    system: SistemaEntity,
    editSystem: (Int) -> Unit,
    deleteSystem: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(5f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ID: ${system.sistemaId}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Name: ${system.nombre}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
            }

            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Opciones")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                DropdownMenuItem(
                    text = { Text("Editar") },
                    leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = "Editar") },
                    onClick = {
                        expanded = false
                        editSystem(system.sistemaId!!)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Eliminar") },
                    leadingIcon = { Icon(Icons.Filled.Delete, contentDescription = "Eliminar") },
                    onClick = {
                        expanded = false
                        deleteSystem(system.sistemaId!!)
                    }
                )
            }
        }
    }
}
