package ucne.edu.nathalygoris_p1_ap2.presentation.sistemas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ucne.edu.nathalygoris_p1_ap2.data.local.entities.SistemaEntity
import ucne.edu.nathalygoris_p1_ap2.data.repository.SistemaRepository
import javax.inject.Inject


@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val sistemaRepository: SistemaRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()
    init{
        getSistemas()
    }
    fun getSistemas(){
        viewModelScope.launch {
            sistemaRepository.getAll().collect{sistemas ->
                _uiState.update {
                    it.copy(sistemas = sistemas)
                }
            }
        }
    }
    fun saveSistemas(){
        viewModelScope.launch {
            if(_uiState.value.nombre.isBlank()){
                _uiState.update {
                    it.copy(errorMessage = "Este campo no debe estar vacio.", successMessage = null)
                }
                return@launch
            }
            try {
                sistemaRepository.saveSistema(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(successMessage = "Se ha guardado correctamente.", errorMessage = null)
                }
                nuevoSistema()
            }catch (e: Exception){
                _uiState.update {
                    it.copy(errorMessage = "No se ha podido guardar.", successMessage = null)
                }
            }
        }
    }
    fun nuevoSistema(){
        _uiState.update {
            it.copy(
                sistemaId = null,
                nombre = ""
            )
        }
    }
    fun deleteSistema(){
        viewModelScope.launch {
            try{
                sistemaRepository.delete(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(successMessage = "Se ha guardado correctamente.", errorMessage = null)
                }
                nuevoSistema()
            }catch (e: Exception){
                _uiState.update {
                    it.copy(errorMessage = "No se ha podido guardar, verifique nuevamente.", successMessage = null)
                }
            }
        }
    }
    fun selectSistema(sistemaId: Int){
        viewModelScope.launch {
            val sistema = sistemaRepository.find(sistemaId)
            if(sistemaId > 0){
                _uiState.update {
                    it.copy(
                        sistemaId = sistema?.sistemaId,
                        nombre = sistema?.nombre ?:""
                    )
                }
            }
        }
    }
    fun onNombreChange(nombre: String){
        _uiState.update {
            it.copy(nombre = nombre)
        }
    }
    fun clearMessages(){
        _uiState.update {
            it.copy(errorMessage = null, successMessage = null)
        }
    }
    fun UiState.toEntity() = SistemaEntity(
        sistemaId = sistemaId,
        nombre = nombre
    )
    data class UiState(
        val sistemaId: Int? = null,
        val nombre: String = "",
        val errorMessage: String? = null,
        val successMessage: String? = null,
        val sistemas: List<SistemaEntity> = emptyList()
    )
}