package ucne.edu.nathalygoris_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Sistemas")
data class SistemaEntity(
    @PrimaryKey
    val sistemaId: Int? = null,
    val nombre: String = "",
    val precio: Double
)
