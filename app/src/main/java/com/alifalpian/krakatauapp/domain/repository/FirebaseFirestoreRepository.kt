package com.alifalpian.krakatauapp.domain.repository

import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.MaintenanceCheckPoint
import com.alifalpian.krakatauapp.domain.model.MaintenanceHistory
import com.alifalpian.krakatauapp.domain.model.MaintenanceSafetyUse
import com.alifalpian.krakatauapp.domain.model.MaintenanceTools
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.TechnicianDashboardEquipment
import com.alifalpian.krakatauapp.domain.model.User
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.flow.Flow

interface FirebaseFirestoreRepository {

    fun getUser(uid: String): Flow<Resource<User>>

    fun getUserByDocumentId(documentId: String): Flow<Resource<User>>

    fun getEquipment(equipmentDocumentId: String): Flow<Resource<Equipment>>

    fun getTechnicianDashboardEquipments(technicianDocumentId: String): Flow<Resource<List<TechnicianDashboardEquipment>>>

    fun getEquipmentsWillBeMaintenance(technicianDocumentId: String): Flow<Resource<List<Equipment>>>

    fun getEquipmentsHasBeenMaintenance(technicianDocumentId: String): Flow<Resource<List<Equipment>>>

    fun getWaitingForApprovalEquipmentsMaintenance(employeeDocumentId: String): Flow<Resource<List<Equipment>>>

    fun getHasBeenApprovedEquipmentsMaintenance(employeeDocumentId: String): Flow<Resource<List<Equipment>>>

    fun getMaintenanceHistory(maintenanceHistoryDocumentId: String): Flow<Resource<MaintenanceHistory>>

    fun getMaintenanceCheckPoint(checkPointId: String): Flow<Resource<List<MaintenanceCheckPoint>>>

    fun getMaintenanceCheckPointHistory(checkPointId: String, maintenanceCheckPointHistoryDocumentId: String): Flow<Resource<List<MaintenanceCheckPoint>>>

    fun getMaintenanceTools(maintenanceHistoryDocumentId: String): Flow<Resource<List<MaintenanceTools>>>

    fun getMaintenanceSafetyUse(maintenanceHistoryDocumentId: String): Flow<Resource<List<MaintenanceSafetyUse>>>

    fun submitMaintenance(
        equipmentDocumentId: String,
        maintenanceCheckPointType: String,
        technicianDocumentId: String,
        employeeDocumentId: String,
        equipmentType: String,
        maintenanceCheckPoints: List<MaintenanceCheckPoint>,
        maintenanceTools: List<MaintenanceTools>,
        maintenanceSafetyUse: List<MaintenanceSafetyUse>,
        equipmentWillMaintenanceDocumentId: String,
        planDuration: FieldValue
    ): Flow<Resource<String>>

    fun getEmployeeEquipments(uid: String): Flow<Resource<List<Equipment>>>

    fun acceptMaintenanceEquipments(maintenanceHistoryDocumentId: String): Flow<Resource<String>>

    fun rejectMaintenanceEquipments(maintenanceHistoryDocumentId: String): Flow<Resource<String>>

}