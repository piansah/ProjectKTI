package com.alifalpian.krakatauapp.presentation.technician.maintenance.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifalpian.krakatauapp.domain.model.MaintenanceCheckPoint
import com.alifalpian.krakatauapp.domain.model.MaintenanceSafetyUse
import com.alifalpian.krakatauapp.domain.model.MaintenanceTools
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaintenanceFormTechnicianViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    var maintenanceFormTechnicianUiState = MutableStateFlow(MaintenanceFormTechnicianUiState())
        private set

    val buttonSubmitLoadingState get() = maintenanceFormTechnicianUiState.map {
        it.submitMaintenance is Resource.Loading
    }

    fun getEquipment(equipmentDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getEquipment(equipmentDocumentId).collect { resource ->
                maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                    equipment = resource
                )
            }
        }
    }

    fun getUser(uid: String) {
        viewModelScope.launch {
            homeUseCase.getUser(uid).collect { resource ->
                maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                    user = resource
                )
            }
        }
    }

    fun getMaintenanceHistory(maintenanceHistoryDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getMaintenanceHistory(maintenanceHistoryDocumentId).collect { resource ->
                maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                    maintenanceHistory = resource
                )
            }
        }
    }

    fun getMaintenanceCheckPoint(checkPointId: String) {
        viewModelScope.launch {
            homeUseCase.getMaintenanceCheckPoint(checkPointId).collect { resource ->
                maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                    maintenanceCheckPoints = resource
                )
            }
        }
    }

    fun getMaintenanceCheckPointHistory(checkPointId: String, maintenanceCheckPointHistoryDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getMaintenanceCheckPointHistory(checkPointId, maintenanceCheckPointHistoryDocumentId).collect { resource ->
                maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                    maintenanceCheckPoints = resource
                )
            }
        }
    }

    fun getMaintenanceTools(maintenanceHistoryDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getMaintenanceTools(maintenanceHistoryDocumentId).collect { resource ->
                maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                    maintenanceToolsForm = resource
                )
            }
        }
    }

    fun getMaintenanceSafetyUse(maintenanceHistoryDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getMaintenanceSafetyUse(maintenanceHistoryDocumentId).collect { resource ->
                maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                    maintenanceSafetyUseForm = resource
                )
            }
        }
    }

    fun submitMaintenance(
        equipmentDocumentId: String,
        maintenanceCheckPointType: String,
        technicianDocumentId: String,
        employeeDocumentId: String,
        equipmentType: String,
        maintenanceCheckPoints: List<MaintenanceCheckPoint>,
        maintenanceTools: List<MaintenanceTools>,
        maintenanceSafetyUse: List<MaintenanceSafetyUse>,
        equipmentWillMaintenanceDocumentId: String,
    ) {
        viewModelScope.launch {
            homeUseCase.submitMaintenance(
                equipmentDocumentId = equipmentDocumentId,
                maintenanceCheckPointType = maintenanceCheckPointType,
                technicianDocumentId = technicianDocumentId,
                employeeDocumentId = employeeDocumentId,
                equipmentType = equipmentType,
                maintenanceCheckPoints = maintenanceCheckPoints,
                maintenanceTools = maintenanceTools,
                maintenanceSafetyUse = maintenanceSafetyUse,
                equipmentWillMaintenanceDocumentId = equipmentWillMaintenanceDocumentId,
                planDuration = maintenanceFormTechnicianUiState.value.planDuration
            ).collect { resource ->
                maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                    submitMaintenance = resource
                )
            }
        }
    }

    fun setMaintenanceToolsFormToMaintenanceState() {
        maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
            maintenanceToolsForm = Resource.Success(emptyList())
        )
    }

    fun setMaintenanceSafetyUseFormToMaintenanceState() {
        maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
            maintenanceSafetyUseForm = Resource.Success(emptyList())
        )
    }

    fun updateMaintenanceCheckPoints(maintenanceCheckPoint: MaintenanceCheckPoint, position: Int) {
        var maintenanceCheckPoints = maintenanceFormTechnicianUiState.value.maintenanceCheckPoints
        if (maintenanceCheckPoints is Resource.Success) {
            val updatedMaintenanceCheckPoints = maintenanceCheckPoints.data.toMutableList()
            updatedMaintenanceCheckPoints[position] = maintenanceCheckPoint
            maintenanceCheckPoints = Resource.Success(updatedMaintenanceCheckPoints)
            maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                maintenanceCheckPoints = maintenanceCheckPoints
            )
        }
    }

    fun addToolsMaintenanceForm() {
        val maintenanceToolsFormResource = maintenanceFormTechnicianUiState.value.maintenanceToolsForm
        if (maintenanceToolsFormResource is Resource.Success) {
            val maintenanceToolsForm = mutableListOf(MaintenanceTools())
            maintenanceToolsForm.addAll(maintenanceToolsFormResource.data)
            maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                maintenanceToolsForm = Resource.Success(maintenanceToolsForm)
            )

        }
    }

    fun addSafetyMaintenanceTools() {
        val safetyMaintenanceToolsResource = maintenanceFormTechnicianUiState.value.maintenanceSafetyUseForm
        if (safetyMaintenanceToolsResource is Resource.Success) {
            val safetyMaintenanceTools = mutableListOf(MaintenanceSafetyUse())
            safetyMaintenanceTools.addAll(safetyMaintenanceToolsResource.data)
            maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                maintenanceSafetyUseForm = Resource.Success(safetyMaintenanceTools)
            )

        }
    }

//    fun removeLastToolsMaintenanceForm() {
//        val toolsMaintenance = maintenanceFormTechnicianUiState.value.maintenanceToolsForm.toMutableList()
//        toolsMaintenance.removeLast()
//        maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
//            maintenanceToolsForm = toolsMaintenance
//        )
//    }

//    fun removeLastSafetyMaintenanceTools() {
//        val safetyMaintenanceTools = maintenanceFormTechnicianUiState.value.maintenanceSafetyUseForm.toMutableList()
//        safetyMaintenanceTools.removeLast()
//        maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
//            maintenanceSafetyUseForm = safetyMaintenanceTools
//        )
//    }

    fun updateToolsMaintenanceForm(index: Int, maintenanceTools: MaintenanceTools) {
        val maintenanceToolsFormResource = maintenanceFormTechnicianUiState.value.maintenanceToolsForm
        if (maintenanceToolsFormResource is Resource.Success) {
            val maintenanceToolsForm = maintenanceToolsFormResource.data.toMutableList()
            maintenanceToolsForm[index] = maintenanceTools
            maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                maintenanceToolsForm = Resource.Success(maintenanceToolsForm)
            )
        }
    }

    fun updateSafetyMaintenanceForm(index: Int, safetyTools: MaintenanceSafetyUse) {
        val maintenanceSafetyUseResource = maintenanceFormTechnicianUiState.value.maintenanceSafetyUseForm
        if (maintenanceSafetyUseResource is Resource.Success) {
            val maintenanceSafetyUse = maintenanceSafetyUseResource.data.toMutableList()
            maintenanceSafetyUse[index] = safetyTools
            maintenanceFormTechnicianUiState.value = maintenanceFormTechnicianUiState.value.copy(
                maintenanceSafetyUseForm = Resource.Success(maintenanceSafetyUse)
            )

        }
    }

}