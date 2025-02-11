package ucne.edu.nathalygoris_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow
import ucne.edu.nathalygoris_p1_ap2.data.local.entities.SistemaEntity

@Dao
interface SistemaDao {
    @Upsert
    suspend fun save(sistema: SistemaEntity)

    @Query("""
        SELECT * FROM Sistemas
        WHERE sistemaId = :id
        LIMIT 1
    """)
    suspend fun find(id: Int): SistemaEntity?

    @Delete
    suspend fun delete(sistema: SistemaEntity)

    @Query("SELECT * FROM Sistemas")
    fun getAll(): Flow<List<SistemaEntity>>
}
