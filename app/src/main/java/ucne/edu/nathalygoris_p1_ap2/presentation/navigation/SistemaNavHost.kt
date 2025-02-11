package ucne.edu.nathalygoris_p1_ap2.presentation.navigation


import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ucne.edu.nathalygoris_p1_ap2.data.local.database.SistemaDb
import ucne.edu.nathalygoris_p1_ap2.data.repository.SistemaRepository
import ucne.edu.nathalygoris_p1_ap2.presentation.sistemas.DeleteSistemaScreen
import ucne.edu.nathalygoris_p1_ap2.presentation.sistemas.EditSistemaScreen
import ucne.edu.nathalygoris_p1_ap2.presentation.sistemas.SistemaListScreen
import ucne.edu.nathalygoris_p1_ap2.presentation.sistemas.SistemaScreen
@Composable
fun registro_sistema(sistemaDb: SistemaDb, navHostController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val entityList by sistemaDb.sistemaDao().getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )

    val scope = rememberCoroutineScope()
    val entityRepository = SistemaRepository(sistemaDb)

    NavHost(
        navController = navHostController,
        startDestination = Screen.SistemaList
    ) {
        composable<Screen.SistemaList> {
            SistemaListScreen(
                drawerState = drawerState,
                scope = scope,
                createSistema = {
                    navHostController.navigate(Screen.Sistemas(0))
                },
                onEditSistema = { sistema ->
                    navHostController.navigate(Screen.EditSistemas(sistema))
                },
                onDeleteSistema = { sistema ->
                    navHostController.navigate(Screen.DeleteSistemas(sistema))
                }
            )
        }

        composable<Screen.Sistemas> {
            val args = it.toRoute<Screen.Sistemas>()
            SistemaScreen(
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.EditSistemas> {
            val args = it.toRoute<Screen.EditSistemas>()
            EditSistemaScreen(
                sistemaId = args.sistemaId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.DeleteSistemas> {
            val args = it.toRoute<Screen.DeleteSistemas>()
            DeleteSistemaScreen(
                sistemaId = args.sistemaId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}
