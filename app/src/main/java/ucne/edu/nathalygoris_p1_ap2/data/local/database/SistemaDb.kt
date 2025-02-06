package ucne.edu.nathalygoris_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import ucne.edu.nathalygoris_p1_ap2.data.local.dao.SistemasDao

@Database(
    entities = [Entity::class],
    version = 1,
    exportSchema = false
)

abstract class SistemaDb(): RoomDatabase(){
    abstract fun sistemaDao(): SistemasDao
}