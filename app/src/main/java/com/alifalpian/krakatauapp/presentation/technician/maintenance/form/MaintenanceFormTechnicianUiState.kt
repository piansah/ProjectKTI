package com.alifalpian.krakatauapp.presentation.technician.maintenance.form

import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.MaintenanceCheckPoint
import com.alifalpian.krakatauapp.domain.model.MaintenanceHistory
import com.alifalpian.krakatauapp.domain.model.MaintenanceSafetyUse
import com.alifalpian.krakatauapp.domain.model.MaintenanceTools
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.User
import com.google.firebase.firestore.FieldValue

data class MaintenanceFormTechnicianUiState(
    val equipment: Resource<Equipment> = Resource.Loading,
    val user: Resource<User> = Resource.Loading,
    val technician: Resource<User> = Resource.Loading,
    val maintenanceHistory: Resource<MaintenanceHistory> = Resource.Loading,
    val maintenanceCheckPoints: Resource<List<MaintenanceCheckPoint>> = Resource.Loading,
    val maintenanceToolsForm: Resource<List<MaintenanceTools>> = Resource.Loading,
    val maintenanceSafetyUseForm: Resource<List<MaintenanceSafetyUse>> = Resource.Loading,
    val submitMaintenance: Resource<String> = Resource.Idling,
    val planDuration: FieldValue = FieldValue.serverTimestamp()
)
