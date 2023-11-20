package com.alifalpian.krakatauapp.presentation.empoyee.dashboard

import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.User
import com.google.firebase.auth.FirebaseUser

data class DashboardEmployeeUiState(
    val loggedUser: FirebaseUser? = null,
    val user: Resource<User> = Resource.Idling,
    val equipments: Resource<List<Equipment>> = Resource.Idling,
    val signOut: Resource<Unit> = Resource.Idling
)
