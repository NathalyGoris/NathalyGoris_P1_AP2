package ucne.edu.nathalygoris_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Entity")
data class SistemaEntity(
    @PrimaryKey
    val entityId: Int? = null,
    val sistemaId: Int? = null,
    val nombre: String = ""
)
