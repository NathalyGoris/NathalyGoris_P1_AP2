package ucne.edu.nathalygoris_p1_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ucne.edu.nathalygoris_p1_ap2.ui.theme.NathalyGoris_P1_AP2Theme
import androidx.room.Room
import ucne.edu.nathalygoris_p1_ap2.data.local.database.SistemaDb
import ucne.edu.nathalygoris_p1_ap2.presentation.navigation.registro_sistema

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NathalyGoris_P1_AP2Theme {
                val navHost = rememberNavController()
                val sistemaDb = Room.databaseBuilder(
                    applicationContext,
                    SistemaDb::class.java,
                    "SistemaDb"
                ).build()

                registro_sistema(
                    navHostController = navHost,
                    sistemaDb = sistemaDb
                )
            }
        }
    }
}


