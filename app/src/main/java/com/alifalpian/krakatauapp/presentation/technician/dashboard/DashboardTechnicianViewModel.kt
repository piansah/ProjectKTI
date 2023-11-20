package com.alifalpian.krakatauapp.presentation.technician.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifalpian.krakatauapp.domain.usecase.HomeUseCase
import com.alifalpian.krakatauapp.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardTechnicianViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var dashboardTechnicianUiState = MutableStateFlow(DashboardTechnicianUiState())
        private set

    fun getLoggedUser() {
        viewModelScope.launch {
            homeUseCase.isUserLogged().collect { resource ->
                dashboardTechnicianUiState.value = dashboardTechnicianUiState.value.copy(
                    loggedUser = resource
                )
            }
        }
    }

    fun getUser(uid: String) {
        viewModelScope.launch {
            homeUseCase.getUser(uid).collect { resource ->
                dashboardTechnicianUiState.value = dashboardTechnicianUiState.value.copy(
                    user = resource
                )
            }
        }
    }

    fun getTechnicianDashboardEquipments(technicianDocumentId: String) {
        viewModelScope.launch {
            homeUseCase.getTechnicianDashboardEquipments(technicianDocumentId).collect { resource ->
                dashboardTechnicianUiState.value = dashboardTechnicianUiState.value.copy(
                    dashboardEquipments = resource
                )
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            loginUseCase.signOut().collect { resource ->
                dashboardTechnicianUiState.value = dashboardTechnicianUiState.value.copy(
                    signOut = resource
                )
            }
        }
    }

}