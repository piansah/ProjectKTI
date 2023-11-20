package com.alifalpian.krakatauapp.presentation.empoyee.maintenance.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaintenanceFormEmployeeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    var maintenanceFormEmployeeUiState = MutableStateFlow(MaintenanceFormEmployeeUiState())
        private set

    val buttonSubmitAndRejectLoadingState get() = maintenanceFormEmployeeUiState.map {
        it.acceptMaintenance is Resource.Loading || it.rejectMaintenance is Resource.Loading
    }

    fun getEquipment(equipmentDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getEquipment(equipmentDocumentId).collect { resource ->
                maintenanceFormEmployeeUiState.value = maintenanceFormEmployeeUiState.value.copy(
                    equipment = resource
                )
            }
        }
    }

    fun getEmployee(documentId: String) {
        viewModelScope.launch {
            homeUseCase.getUserByDocumentId(documentId).collect { resource ->
                maintenanceFormEmployeeUiState.value = maintenanceFormEmployeeUiState.value.copy(
                    employee = resource
                )
            }
        }
    }

    fun getTechnician(documentId: String) {
        viewModelScope.launch {
            homeUseCase.getUserByDocumentId(documentId).collect { resource ->
                maintenanceFormEmployeeUiState.value = maintenanceFormEmployeeUiState.value.copy(
                    technician = resource
                )
            }
        }
    }

    fun getMaintenanceHistory(maintenanceHistoryDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getMaintenanceHistory(maintenanceHistoryDocumentId).collect { resource ->
                maintenanceFormEmployeeUiState.value = maintenanceFormEmployeeUiState.value.copy(
                    maintenanceHistory = resource
                )
            }
        }
    }

    fun getMaintenanceCheckPoint(checkPointId: String) {
        viewModelScope.launch {
            homeUseCase.getMaintenanceCheckPoint(checkPointId).collect { resource ->
                maintenanceFormEmployeeUiState.value = maintenanceFormEmployeeUiState.value.copy(
                    maintenanceCheckPoints = resource
                )
            }
        }
    }

    fun getMaintenanceCheckPointHistory(checkPointId: String, maintenanceCheckPointHistoryDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getMaintenanceCheckPointHistory(checkPointId, maintenanceCheckPointHistoryDocumentId).collect { resource ->
                maintenanceFormEmployeeUiState.value = maintenanceFormEmployeeUiState.value.copy(
                    maintenanceCheckPoints = resource
                )
            }
        }
    }

    fun getMaintenanceTools(maintenanceHistoryDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getMaintenanceTools(maintenanceHistoryDocumentId).collect { resource ->
                maintenanceFormEmployeeUiState.value = maintenanceFormEmployeeUiState.value.copy(
                    maintenanceToolsForm = resource
                )
            }
        }
    }

    fun getMaintenanceSafetyUse(maintenanceHistoryDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getMaintenanceSafetyUse(maintenanceHistoryDocumentId).collect { resource ->
                maintenanceFormEmployeeUiState.value = maintenanceFormEmployeeUiState.value.copy(
                    maintenanceSafetyUseForm = resource
                )
            }
        }
    }

    fun acceptMaintenanceEquipments(maintenanceHistoryDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.acceptMaintenanceEquipments(maintenanceHistoryDocumentId).collect { resource ->
                maintenanceFormEmployeeUiState.value = maintenanceFormEmployeeUiState.value.copy(
                    acceptMaintenance = resource
                )
            }
        }
    }

    fun rejectMaintenanceEquipments(maintenanceHistoryDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.rejectMaintenanceEquipments(maintenanceHistoryDocumentId).collect { resource ->
                maintenanceFormEmployeeUiState.value = maintenanceFormEmployeeUiState.value.copy(
                    rejectMaintenance = resource
                )
            }
        }
    }

}