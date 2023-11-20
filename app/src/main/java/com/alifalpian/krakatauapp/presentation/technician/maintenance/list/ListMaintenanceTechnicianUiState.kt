package com.alifalpian.krakatauapp.presentation.technician.maintenance.list

import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.User
import com.google.firebase.auth.FirebaseUser

data class ListMaintenanceTechnicianUiState(
    val loggedUser: FirebaseUser? = null,
    val user: Resource<User> = Resource.Idling,
    val equipmentsWillMaintenance: Resource<List<Equipment>> = Resource.Loading,
    val equipmentsHasBeenMaintenance: Resource<List<Equipment>> = Resource.Loading,
)
