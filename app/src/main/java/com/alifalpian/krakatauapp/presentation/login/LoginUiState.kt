package com.alifalpian.krakatauapp.presentation.login

import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.User
import com.alifalpian.krakatauapp.util.emptyString
import com.google.firebase.auth.AuthResult

data class LoginUiState(
    val email: String = emptyString(),
    val password: String = emptyString(),
    val loginResult: Resource<AuthResult> = Resource.Idling,
    val loggedUser: Resource<User> = Resource.Idling
)
