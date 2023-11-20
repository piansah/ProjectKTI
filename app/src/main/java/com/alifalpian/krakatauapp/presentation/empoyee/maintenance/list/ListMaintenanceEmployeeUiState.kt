package com.alifalpian.krakatauapp.presentation.empoyee.maintenance.list

import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.User

data class ListMaintenanceEmployeeUiState(
    val user: Resource<User> = Resource.Loading,
    val waitingForApprovalMaintenance: Resource<List<Equipment>> = Resource.Loading,
    val hasBeenApprovedMaintenance: Resource<List<Equipment>> = Resource.Loading,
)
