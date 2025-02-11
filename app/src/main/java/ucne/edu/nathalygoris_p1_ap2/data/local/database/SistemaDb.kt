package ucne.edu.nathalygoris_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ucne.edu.nathalygoris_p1_ap2.data.local.dao.SistemaDao
import ucne.edu.nathalygoris_p1_ap2.data.local.entities.SistemaEntity

@Database(
    entities = [SistemaEntity::class],
    version = 3,
    exportSchema = false
)

abstract class SistemaDb(): RoomDatabase(){
    abstract fun sistemaDao(): SistemaDao
}