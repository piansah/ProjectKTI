package com.alifalpian.krakatauapp.presentation.technician.dashboard

import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.TechnicianDashboardEquipment
import com.alifalpian.krakatauapp.domain.model.User
import com.google.firebase.auth.FirebaseUser

data class DashboardTechnicianUiState(
    val loggedUser: FirebaseUser? = null,
    val user: Resource<User> = Resource.Idling,
    val dashboardEquipments: Resource<List<TechnicianDashboardEquipment>> = Resource.Idling,
    val signOut: Resource<Unit> = Resource.Idling
)
