package ucne.edu.nathalygoris_p1_ap2.data.repository

import javax.inject.Inject
import ucne.edu.nathalygoris_p1_ap2.data.local.database.SistemaDb
import kotlinx.coroutines.flow.Flow
import ucne.edu.nathalygoris_p1_ap2.data.local.entities.SistemaEntity


class SistemaRepository @Inject constructor(
    private val sistemaDb: SistemaDb
) {
    suspend fun saveSistema(sistema: SistemaEntity){
        sistemaDb.sistemaDao().save(sistema)
    }
    suspend fun find(id: Int): SistemaEntity?{
        return sistemaDb.sistemaDao().find(id)
    }
    suspend fun delete(sistema: SistemaEntity){
        return sistemaDb.sistemaDao().delete(sistema)
    }
    fun getAll(): Flow<List<SistemaEntity>> {
        return sistemaDb.sistemaDao().getAll()
    }
}