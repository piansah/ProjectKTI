package com.alifalpian.krakatauapp.domain.model

import com.alifalpian.krakatauapp.util.emptyString

data class User(
    val documentId: String = emptyString(),
    val type: String = emptyString(),
    val photo: String = emptyString(),
    val name: String = emptyString(),
    val division: String = emptyString(),
    val nik: String = emptyString(),
)
