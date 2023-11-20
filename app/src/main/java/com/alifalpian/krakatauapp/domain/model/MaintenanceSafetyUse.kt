package com.alifalpian.krakatauapp.domain.model

import com.alifalpian.krakatauapp.util.emptyString

data class MaintenanceSafetyUse(
    val documentId: String = emptyString(),
    val description: String = emptyString(),
    val quantity: Int = 0,
    val unitOfMeasurement: Int = 0,
)
