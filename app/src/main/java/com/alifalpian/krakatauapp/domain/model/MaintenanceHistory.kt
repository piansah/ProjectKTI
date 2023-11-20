package com.alifalpian.krakatauapp.domain.model

import com.alifalpian.krakatauapp.util.emptyString
import com.google.firebase.Timestamp

data class MaintenanceHistory(
    val documentId: String = emptyString(),
    val technicianDocumentId: String = emptyString(),
    val employeeDocumentId: String = emptyString(),
    val maintenanceCheckPoint: String = emptyString(),
    val equipmentType: String = emptyString(),
    val equipmentDocumentId: String = emptyString(),
    val date: Timestamp = Timestamp.now(),
    val maintenanceCheckPointHistoryDocumentId: String = emptyString(),
    val status: String = emptyString(),
    val plantDuration: Timestamp = Timestamp.now(),
    val actualDuration: Timestamp = Timestamp.now()
)
