package com.alifalpian.krakatauapp.domain.usecase

import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.User
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun signInWithEmailAndPassword(email: String, password: String): Flow<Resource<AuthResult>>

    fun getUser(uid: String): Flow<Resource<User>>

    fun signOut(): Flow<Resource<Unit>>

}