package com.alifalpian.krakatauapp.domain.model

import com.alifalpian.krakatauapp.util.emptyString

data class MaintenanceEquipment(
    val id: String = emptyString(),
    val order: String = emptyString(),
    val date: String = emptyString(),
    val interval: String = emptyString(),
    val execution: String = emptyString(),
    val location: String = emptyString(),
    val equipmentName: String = emptyString(),
    val technicianName: String = emptyString(),
)