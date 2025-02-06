package ucne.edu.nathalygoris_p1_ap2.data.local.dao


import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow
import ucne.edu.nathalygoris_p1_ap2.data.local.entities.SistemaEntity

@Dao
interface SistemasDao {
    @Upsert
    suspend fun save(entity: SistemaEntity)

    @Query("""
        SELECT * 
        FROM Entity
        WHERE entityId = :id
        LIMIT 1
    """)
    suspend fun find(id: Int): SistemaEntity?

    @Delete
    suspend fun delete(sistema: SistemaEntity)

    @Query("SELECT * FROM Entity")
    fun getAll(): Flow<List<SistemaEntity>>
}