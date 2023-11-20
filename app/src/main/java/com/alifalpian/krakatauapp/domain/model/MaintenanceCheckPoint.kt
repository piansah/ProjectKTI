package com.alifalpian.krakatauapp.domain.model

data class MaintenanceCheckPoint(
    val id: String,
    val isChecked: Boolean = false,
    val text: String
)
