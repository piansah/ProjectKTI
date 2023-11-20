package com.alifalpian.krakatauapp.presentation.empoyee.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifalpian.krakatauapp.domain.usecase.HomeUseCase
import com.alifalpian.krakatauapp.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardEmployeeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var dashboardEmployeeUiState = MutableStateFlow(DashboardEmployeeUiState())
        private set

    fun getLoggedUser() {
        viewModelScope.launch {
            homeUseCase.isUserLogged().collect { resource ->
                dashboardEmployeeUiState.value = dashboardEmployeeUiState.value.copy(
                    loggedUser = resource
                )
            }
        }
    }

    fun getUser(uid: String) {
        viewModelScope.launch {
            homeUseCase.getUser(uid).collect { resource ->
                dashboardEmployeeUiState.value = dashboardEmployeeUiState.value.copy(
                    user = resource
                )
            }
        }
    }

    fun getEmployeeEquipments(uid: String) {
        viewModelScope.launch {
            homeUseCase.getEmployeeEquipments(uid).collect { resource ->
                dashboardEmployeeUiState.value = dashboardEmployeeUiState.value.copy(
                    equipments = resource
                )
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            loginUseCase.signOut().collect { resource ->
                dashboardEmployeeUiState.value = dashboardEmployeeUiState.value.copy(
                    signOut = resource
                )
            }
        }
    }

}