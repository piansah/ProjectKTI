package com.alifalpian.krakatauapp.data.repository

import android.util.Log
import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.MaintenanceCheckPoint
import com.alifalpian.krakatauapp.domain.model.MaintenanceHistory
import com.alifalpian.krakatauapp.domain.model.MaintenanceSafetyUse
import com.alifalpian.krakatauapp.domain.model.MaintenanceTools
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.TechnicianDashboardEquipment
import com.alifalpian.krakatauapp.domain.model.User
import com.alifalpian.krakatauapp.domain.repository.FirebaseFirestoreRepository
import com.alifalpian.krakatauapp.util.emptyString
import com.alifalpian.krakatauapp.util.titleCase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseFirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseFirestoreRepository {

    override fun getUser(uid: String): Flow<Resource<User>> = flow<Resource<User>> {
        emit(Resource.Loading)
        val user = firestore.collection("users").whereEqualTo("uid", uid).get().await().first().let {
            User(
                documentId = it.id,
                type = it.getString("type") ?: emptyString(),
                photo = it.getString("photo") ?: emptyString(),
                name = it.getString("name") ?: emptyString(),
                division = it.getString("division")?.titleCase() ?: emptyString(),
                nik = it.getString("nik") ?: emptyString()
            )
        }
        emit(Resource.Success(user))
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getUserByDocumentId(documentId: String): Flow<Resource<User>> = flow<Resource<User>> {
        emit(Resource.Loading)
        val user = firestore.collection("users").document(documentId).get().await().let {
            User(
                documentId = it.id,
                type = it.getString("type") ?: emptyString(),
                photo = it.getString("photo") ?: emptyString(),
                name = it.getString("name") ?: emptyString(),
                division = it.getString("division")?.titleCase() ?: emptyString(),
                nik = it.getString("nik") ?: emptyString()
            )
        }
        emit(Resource.Success(user))
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getEquipment(equipmentDocumentId: String): Flow<Resource<Equipment>> = flow<Resource<Equipment>> {
        emit(Resource.Loading)
        val equipment = firestore.collection("equipments").document(equipmentDocumentId).get().await().let {
            Equipment(
                documentId = it.id,
                equipment = it.getLong("equipment") ?: 0L,
                date = it.getTimestamp("date") ?: Timestamp.now(),
                interval = it.getString("interval") ?: emptyString(),
                execution = it.getString("execution") ?: emptyString(),
                location = it.getString("location") ?: emptyString(),
                description = it.getString("description") ?: emptyString(),
                type = it.getString("type") ?: emptyString(),
                maintenanceCheckPointType = it.getString("maintenance_check_point_type") ?: emptyString(),
                uid = it.getString("uid") ?: emptyString()
            )
        }
        delay(3000L)
        emit(Resource.Success(equipment))
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getTechnicianDashboardEquipments(technicianDocumentId: String): Flow<Resource<List<TechnicianDashboardEquipment>>> = flow<Resource<List<TechnicianDashboardEquipment>>> {
        emit(Resource.Loading)
        val equipments = firestore.collection("equipments").get().await().map {
            Equipment(
                documentId = it.id,
                equipment = it.getLong("equipment") ?: 0L,
                date = it.getTimestamp("date") ?: Timestamp.now(),
                interval = it.getString("interval") ?: emptyString(),
                execution = it.getString("execution") ?: emptyString(),
                location = it.getString("location") ?: emptyString(),
                description = it.getString("description") ?: emptyString(),
                type = it.getString("type") ?: emptyString(),
                maintenanceCheckPointType = it.getString("maintenance_check_point_type") ?: emptyString(),
                uid = it.getString("uid") ?: emptyString()
            )
        }
        val equipmentTypes = equipments.distinctBy { it.type }
            .map { it.type }
        val technicianDashboardEquipments = equipmentTypes.map {
            TechnicianDashboardEquipment(
                name = it.titleCase(),
                count = equipments.count { document -> document.type == it },
                maintenanceCount = firestore.collection("maintenance_history").whereEqualTo("technician_document_id", technicianDocumentId)
                    .whereEqualTo("type", it).get().await().count(),
                id = UUID.randomUUID().toString()
            )
        }
        if (technicianDashboardEquipments.isNotEmpty()) {
            emit(Resource.Success(technicianDashboardEquipments))
        } else {
            emit(Resource.Empty)
        }
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getEquipmentsWillBeMaintenance(technicianDocumentId: String): Flow<Resource<List<Equipment>>> = flow<Resource<List<Equipment>>> {
        emit(Resource.Loading)
        val equipmentsWillBeMaintenance = firestore.collection("equipment_will_maintenance").whereEqualTo("technician_document_id", technicianDocumentId).get().await()
        val equipments = equipmentsWillBeMaintenance.map {
            val equipmentId = it.getLong("equipment")
            val equipmentSnapshot = firestore.collection("equipments").whereEqualTo("equipment", equipmentId).limit(1).get().await().first()
            val equipment = Equipment(
                documentId = equipmentSnapshot.id,
                equipment = equipmentSnapshot.getLong("equipment") ?: 0L,
                date = equipmentSnapshot.getTimestamp("date") ?: Timestamp.now(),
                interval = equipmentSnapshot.getString("interval") ?: emptyString(),
                execution = equipmentSnapshot.getString("execution") ?: emptyString(),
                location = equipmentSnapshot.getString("location") ?: emptyString(),
                description = equipmentSnapshot.getString("description") ?: emptyString(),
                type = equipmentSnapshot.getString("type") ?: emptyString(),
                maintenanceCheckPointType = it.getString("maintenance_check_point_type") ?: emptyString(),
                uid = equipmentSnapshot.getString("uid") ?: emptyString(),
                equipmentWillMaintenanceDocumentId = it.id
            )
            equipment
        }
        if (equipments.isNotEmpty()) {
            emit(Resource.Success(equipments))
        } else {
            emit(Resource.Empty)
        }
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getEquipmentsHasBeenMaintenance(technicianDocumentId: String): Flow<Resource<List<Equipment>>> = flow<Resource<List<Equipment>>> {
        emit(Resource.Loading)
//        val startDate = Date(2023, 9, 20)
        val maintenanceHistories = firestore.collection("maintenance_history").whereEqualTo("technician_document_id", technicianDocumentId)
//            .whereLessThan("date", Date(2023, 8, 23))
//            .whereGreaterThan("date", startDate)
            .get().await()
        val equipments = maintenanceHistories
            .map {
                MaintenanceHistory(
                    documentId = it.id,
                    technicianDocumentId = it.getString("technician_document_id") ?: emptyString(),
                    maintenanceCheckPoint = it.getString("maintenance_check_point") ?: emptyString(),
                    equipmentType = it.getString("type") ?: emptyString(),
                    equipmentDocumentId = it.getString("equipment_document_id") ?: emptyString(),
                    date = it.getTimestamp("date") ?: Timestamp.now(),
                    maintenanceCheckPointHistoryDocumentId = it.getString("maintenance_check_point_history_document_id") ?: emptyString(),
                    status = it.getString("status")?.titleCase() ?: emptyString()
                )
            }
            .sortedByDescending { it.date }
            .map { maintenanceHistory ->
                val maintenanceHistoryDocumentId = maintenanceHistory.documentId
                val equipmentDocumentId = maintenanceHistory.equipmentDocumentId
                val equipmentSnapshot = firestore.collection("equipments").document(equipmentDocumentId).get().await()
                val equipment = Equipment(
                    documentId = equipmentSnapshot.id,
                    equipment = equipmentSnapshot.getLong("equipment") ?: 0L,
                    date = maintenanceHistory.date,
                    interval = equipmentSnapshot.getString("interval") ?: emptyString(),
                    execution = equipmentSnapshot.getString("execution") ?: emptyString(),
                    location = equipmentSnapshot.getString("location") ?: emptyString(),
                    description = equipmentSnapshot.getString("description") ?: emptyString(),
                    type = equipmentSnapshot.getString("type") ?: emptyString(),
                    maintenanceCheckPointType = maintenanceHistory.maintenanceCheckPoint,
                    uid = equipmentSnapshot.getString("uid") ?: emptyString(),
                    maintenanceHistoryDocumentId = maintenanceHistoryDocumentId,
                    maintenanceStatus = maintenanceHistory.status
                )
                equipment
            }
        if (equipments.isNotEmpty()) {
            emit(Resource.Success(equipments))
        } else {
            emit(Resource.Empty)
        }
    }.catch {
        emit(Resource.Error(it.message))
        Log.d("TAG", "getEquipmentsHasBeenMaintenance: Error = ${it.message}")
    }

    override fun getWaitingForApprovalEquipmentsMaintenance(employeeDocumentId: String): Flow<Resource<List<Equipment>>> = flow<Resource<List<Equipment>>> {
        emit(Resource.Loading)
        val maintenanceHistories = firestore.collection("maintenance_history")
            .whereEqualTo("employee_document_id", employeeDocumentId)
            .whereEqualTo("status", "waiting for approval")
//            .whereLessThan("date", Date(2023, 8, 23))
//            .whereGreaterThan("date", "September 20, 2023 at 9:39:27 PM UTC+7")
            .get().await()
        val equipments = maintenanceHistories
            .map {
                MaintenanceHistory(
                    documentId = it.id,
                    technicianDocumentId = it.getString("technician_document_id") ?: emptyString(),
                    maintenanceCheckPoint = it.getString("maintenance_check_point") ?: emptyString(),
                    equipmentType = it.getString("type") ?: emptyString(),
                    equipmentDocumentId = it.getString("equipment_document_id") ?: emptyString(),
                    date = it.getTimestamp("date") ?: Timestamp.now(),
                    maintenanceCheckPointHistoryDocumentId = it.getString("maintenance_check_point_history_document_id") ?: emptyString(),
                    status = it.getString("status")?.titleCase() ?: emptyString()
                )
            }
            .sortedByDescending { it.date }
            .map { maintenanceHistory ->
                val maintenanceHistoryDocumentId = maintenanceHistory.documentId
                val equipmentDocumentId = maintenanceHistory.equipmentDocumentId
                val technicianDocumentId = maintenanceHistory.technicianDocumentId
                val equipmentSnapshot = firestore.collection("equipments").document(equipmentDocumentId).get().await()
                val technicianSnapshot = firestore.collection("users").document(technicianDocumentId).get().await()
                val equipment = Equipment(
                    documentId = equipmentSnapshot.id,
                    equipment = equipmentSnapshot.getLong("equipment") ?: 0L,
                    date = maintenanceHistory.date,
                    interval = equipmentSnapshot.getString("interval") ?: emptyString(),
                    execution = equipmentSnapshot.getString("execution") ?: emptyString(),
                    location = equipmentSnapshot.getString("location") ?: emptyString(),
                    description = equipmentSnapshot.getString("description") ?: emptyString(),
                    type = equipmentSnapshot.getString("type") ?: emptyString(),
                    maintenanceCheckPointType = maintenanceHistory.maintenanceCheckPoint,
                    uid = equipmentSnapshot.getString("uid") ?: emptyString(),
                    maintenanceHistoryDocumentId = maintenanceHistoryDocumentId,
                    maintenanceStatus = maintenanceHistory.status,
                    technicianName = technicianSnapshot.getString("name") ?: emptyString()
                )
                equipment
            }
        if (equipments.isNotEmpty()) {
            emit(Resource.Success(equipments))
        } else {
            emit(Resource.Empty)
        }
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getHasBeenApprovedEquipmentsMaintenance(employeeDocumentId: String): Flow<Resource<List<Equipment>>> = flow<Resource<List<Equipment>>> {
        emit(Resource.Loading)
        val maintenanceHistories = firestore.collection("maintenance_history")
            .whereEqualTo("employee_document_id", employeeDocumentId)
            .whereNotEqualTo("status", "waiting for approval")
//            .whereLessThan("date", Date(2023, 8, 23))
//            .whereGreaterThan("date", "September 20, 2023 at 9:39:27 PM UTC+7")
            .get().await()
        val equipments = maintenanceHistories
            .map {
                MaintenanceHistory(
                    documentId = it.id,
                    technicianDocumentId = it.getString("technician_document_id") ?: emptyString(),
                    maintenanceCheckPoint = it.getString("maintenance_check_point") ?: emptyString(),
                    equipmentType = it.getString("type") ?: emptyString(),
                    equipmentDocumentId = it.getString("equipment_document_id") ?: emptyString(),
                    date = it.getTimestamp("date") ?: Timestamp.now(),
                    maintenanceCheckPointHistoryDocumentId = it.getString("maintenance_check_point_history_document_id") ?: emptyString(),
                    status = it.getString("status")?.titleCase() ?: emptyString()
                )
            }
            .sortedByDescending { it.date }
            .map { maintenanceHistory ->
                val maintenanceHistoryDocumentId = maintenanceHistory.documentId
                val equipmentDocumentId = maintenanceHistory.equipmentDocumentId
                val technicianDocumentId = maintenanceHistory.technicianDocumentId
                val equipmentSnapshot = firestore.collection("equipments").document(equipmentDocumentId).get().await()
                val technicianSnapshot = firestore.collection("users").document(technicianDocumentId).get().await()
                val equipment = Equipment(
                    documentId = equipmentSnapshot.id,
                    equipment = equipmentSnapshot.getLong("equipment") ?: 0L,
                    date = maintenanceHistory.date,
                    interval = equipmentSnapshot.getString("interval") ?: emptyString(),
                    execution = equipmentSnapshot.getString("execution") ?: emptyString(),
                    location = equipmentSnapshot.getString("location") ?: emptyString(),
                    description = equipmentSnapshot.getString("description") ?: emptyString(),
                    type = equipmentSnapshot.getString("type") ?: emptyString(),
                    maintenanceCheckPointType = maintenanceHistory.maintenanceCheckPoint,
                    uid = equipmentSnapshot.getString("uid") ?: emptyString(),
                    maintenanceHistoryDocumentId = maintenanceHistoryDocumentId,
                    maintenanceStatus = maintenanceHistory.status,
                    technicianName = technicianSnapshot.getString("name") ?: emptyString()
                )
                equipment
            }
        if (equipments.isNotEmpty()) {
            emit(Resource.Success(equipments))
        } else {
            emit(Resource.Empty)
        }
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getMaintenanceHistory(maintenanceHistoryDocumentId: String): Flow<Resource<MaintenanceHistory>> = flow<Resource<MaintenanceHistory>> {
        emit(Resource.Loading)
        val maintenanceHistory = firestore.collection("maintenance_history").document(maintenanceHistoryDocumentId).get().await().let {
            MaintenanceHistory(
                documentId = it.id,
                technicianDocumentId = it.getString("technician_document_id") ?: emptyString(),
                maintenanceCheckPoint = it.getString("maintenance_check_point") ?: emptyString(),
                equipmentDocumentId = it.getString("equipment_document_id") ?: emptyString(),
                equipmentType = it.getString("type") ?: emptyString(),
                date = it.getTimestamp("date") ?: Timestamp.now(),
                maintenanceCheckPointHistoryDocumentId = it.getString("maintenance_check_point_history_document_id") ?: emptyString(),
                employeeDocumentId = it.getString("employee_document_id") ?: emptyString(),
                status = it.getString("status") ?: emptyString(),
                plantDuration = it.getTimestamp("plant_duration") ?: Timestamp.now(),
                actualDuration = it.getTimestamp("actual_duration") ?: Timestamp.now()
            )
        }
        emit(Resource.Success(maintenanceHistory))
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getMaintenanceCheckPoint(checkPointId: String): Flow<Resource<List<MaintenanceCheckPoint>>> = flow<Resource<List<MaintenanceCheckPoint>>> {
        emit(Resource.Loading)
        val maintenanceCheckPoints = firestore.collection("maintenance_check_point").document(checkPointId).get().await().let {
            val checkPoints: List<String> = it.get("check_points") as List<String>
            checkPoints.map { check ->
                MaintenanceCheckPoint(
                    id = UUID.randomUUID().toString(),
                    text = check
                )
            }
        }
        if (maintenanceCheckPoints.isNotEmpty()) {
            emit(Resource.Success(maintenanceCheckPoints))
        } else {
            emit(Resource.Empty)
        }
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getMaintenanceCheckPointHistory(checkPointId: String, maintenanceCheckPointHistoryDocumentId: String): Flow<Resource<List<MaintenanceCheckPoint>>> = flow<Resource<List<MaintenanceCheckPoint>>> {
        emit(Resource.Loading)
        val maintenanceCheckPoints = firestore.collection("maintenance_check_point").document(checkPointId).get().await().let {
            val checkPoints: List<String> = it.get("check_points") as List<String>
            checkPoints
        }
        val maintenanceCheckPointHistory = firestore.collection("maintenance_check_point_history").document(maintenanceCheckPointHistoryDocumentId).get().await().let {
            val checkPoints: List<Boolean> = it.get("maintenance_check_point_history") as List<Boolean>
            checkPoints.mapIndexed { index, check ->
                MaintenanceCheckPoint(
                    id = UUID.randomUUID().toString(),
                    text = maintenanceCheckPoints[index],
                    isChecked = check
                )
            }
        }
        if (maintenanceCheckPointHistory.isNotEmpty()) {
            emit(Resource.Success(maintenanceCheckPointHistory))
        } else {
            emit(Resource.Empty)
        }
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getMaintenanceTools(maintenanceHistoryDocumentId: String): Flow<Resource<List<MaintenanceTools>>> = flow<Resource<List<MaintenanceTools>>> {
        emit(Resource.Loading)
        val maintenanceTools = firestore.collection("maintenance_tools").whereEqualTo("maintenance_history_document_id", maintenanceHistoryDocumentId).get().await()
            .map {
                MaintenanceTools(
                    documentId = it.id,
                    description = it.getString("description") ?: emptyString(),
                    quantity = it.get("quantity")?.toString()?.toInt() ?: 0,
                    unitOfMeasurement = it.get("unit_of_measurement")?.toString()?.toInt() ?: 0
                )
            }
        if (maintenanceTools.isNotEmpty()) {
            emit(Resource.Success(maintenanceTools))
        } else {
            emit(Resource.Empty)
        }
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getMaintenanceSafetyUse(maintenanceHistoryDocumentId: String): Flow<Resource<List<MaintenanceSafetyUse>>> = flow<Resource<List<MaintenanceSafetyUse>>> {
        emit(Resource.Loading)
        val maintenanceTools = firestore.collection("maintenance_safety_use").whereEqualTo("maintenance_history_document_id", maintenanceHistoryDocumentId).get().await()
            .map {
                MaintenanceSafetyUse(
                    documentId = it.id,
                    description = it.getString("description") ?: emptyString(),
                    quantity = it.get("quantity")?.toString()?.toInt() ?: 0,
                    unitOfMeasurement = it.get("unit_of_measurement")?.toString()?.toInt() ?: 0
                )
            }
        if (maintenanceTools.isNotEmpty()) {
            emit(Resource.Success(maintenanceTools))
        } else {
            emit(Resource.Empty)
        }
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun submitMaintenance(
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
    ): Flow<Resource<String>> = flow<Resource<String>> {

        emit(Resource.Loading)

        val maintenanceCheckPointsMap = hashMapOf("maintenance_check_point_history" to maintenanceCheckPoints.map { it.isChecked })
        val maintenanceCheckpointsResponse = firestore.collection("maintenance_check_point_history")
            .add(maintenanceCheckPointsMap).await()
        val maintenanceCheckpointDocumentId = maintenanceCheckpointsResponse.id

        val maintenanceHistoryMap = hashMapOf(
            "date" to FieldValue.serverTimestamp(),
            "equipment_document_id" to equipmentDocumentId,
            "maintenance_check_point" to maintenanceCheckPointType,
            "maintenance_check_point_history_document_id" to maintenanceCheckpointDocumentId,
            "technician_document_id" to technicianDocumentId,
            "employee_document_id" to employeeDocumentId,
            "type" to equipmentType,
            "status" to "waiting for approval",
            "plan_duration" to planDuration,
            "actual_duration" to FieldValue.serverTimestamp()
        )
        val maintenanceHistoryResponse = firestore.collection("maintenance_history")
            .add(maintenanceHistoryMap).await()
        val maintenanceHistoryDocumentId = maintenanceHistoryResponse.id

        maintenanceTools.forEach {
            val maintenanceToolsMap = hashMapOf(
                "description" to it.description,
                "maintenance_history_document_id" to maintenanceHistoryDocumentId,
                "quantity" to it.quantity,
                "unit_of_measurement" to it.unitOfMeasurement
            )
            firestore.collection("maintenance_tools")
                .add(maintenanceToolsMap).await()
        }

        maintenanceSafetyUse.forEach {
            val maintenanceSafetyUseMap = hashMapOf(
                "description" to it.description,
                "maintenance_history_document_id" to maintenanceHistoryDocumentId,
                "quantity" to it.quantity,
                "unit_of_measurement" to it.unitOfMeasurement
            )
            firestore.collection("maintenance_safety_use")
                .add(maintenanceSafetyUseMap).await()
        }

        firestore.collection("equipment_will_maintenance").document(equipmentWillMaintenanceDocumentId).delete().await()

        emit(Resource.Success("Success"))

    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun getEmployeeEquipments(uid: String): Flow<Resource<List<Equipment>>> = flow<Resource<List<Equipment>>> {
        emit(Resource.Loading)
        val equipments = firestore.collection("equipments").whereEqualTo("uid", uid).get().await().map {
            val equipment = Equipment(
                documentId = it.id,
                equipment = it.getLong("equipment") ?: 0L,
                date = it.getTimestamp("date") ?: Timestamp.now(),
                interval = it.getString("interval") ?: emptyString(),
                execution = it.getString("execution") ?: emptyString(),
                location = it.getString("location") ?: emptyString(),
                description = it.getString("description") ?: emptyString(),
                type = it.getString("type") ?: emptyString(),
                maintenanceCheckPointType = it.getString("maintenance_check_point_type") ?: emptyString(),
                uid = it.getString("uid") ?: emptyString()
            )
            equipment
        }
        if (equipments.isNotEmpty()) {
            emit(Resource.Success(equipments))
        } else {
            emit(Resource.Empty)
        }
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun acceptMaintenanceEquipments(maintenanceHistoryDocumentId: String): Flow<Resource<String>> = flow<Resource<String>> {
        emit(Resource.Loading)
        firestore.collection("maintenance_history").document(maintenanceHistoryDocumentId)
            .update("status", "accept")
            .await()
        emit(Resource.Success("Success"))
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun rejectMaintenanceEquipments(maintenanceHistoryDocumentId: String): Flow<Resource<String>> = flow<Resource<String>> {
        emit(Resource.Loading)
        firestore.collection("maintenance_history").document(maintenanceHistoryDocumentId)
            .update("status", "reject")
            .await()
        emit(Resource.Success("Success"))
    }.catch {
        emit(Resource.Error(it.message))
    }
}