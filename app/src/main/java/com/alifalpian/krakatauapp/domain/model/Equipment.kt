package com.alifalpian.krakatauapp.domain.model

import com.alifalpian.krakatauapp.util.emptyString
import com.google.firebase.Timestamp

data class Equipment(
    val documentId: String = emptyString(),
    val equipment: Long = 0L,
    val date: Timestamp = Timestamp.now(),
    val interval: String = emptyString(),
    val execution: String = emptyString(),
    val location: String = emptyString(),
    val description: String = emptyString(),
    val type: String = emptyString(),
    val maintenanceCheckPointType: String = emptyString(),
    val uid: String = emptyString(),
    val equipmentWillMaintenanceDocumentId: String = emptyString(),
    val maintenanceHistoryDocumentId: String = emptyString(),
    val maintenanceStatus: String = emptyString(),
    val technicianName: String = emptyString(),
)