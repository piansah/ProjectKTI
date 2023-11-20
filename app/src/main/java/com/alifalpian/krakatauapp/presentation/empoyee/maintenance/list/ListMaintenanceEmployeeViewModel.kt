package com.alifalpian.krakatauapp.presentation.empoyee.maintenance.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifalpian.krakatauapp.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMaintenanceEmployeeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    var dashboardEmployeeUiState = MutableStateFlow(ListMaintenanceEmployeeUiState())
        private set

    fun getUser(uid: String) {
        viewModelScope.launch {
            homeUseCase.getUser(uid).collect { resource ->
                dashboardEmployeeUiState.value = dashboardEmployeeUiState.value.copy(
                    user = resource
                )
            }
        }
    }

    fun getWaitingForApprovalEquipmentsMaintenance(employeeDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getWaitingForApprovalEquipmentsMaintenance(employeeDocumentId).collect { resource ->
                dashboardEmployeeUiState.value = dashboardEmployeeUiState.value.copy(
                    waitingForApprovalMaintenance = resource
                )
            }
        }
    }

    fun getHasBeenApprovedEquipmentsMaintenance(employeeDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getHasBeenApprovedEquipmentsMaintenance(employeeDocumentId).collect { resource ->
                dashboardEmployeeUiState.value = dashboardEmployeeUiState.value.copy(
                    hasBeenApprovedMaintenance = resource
                )
            }
        }
    }

}