package com.alifalpian.krakatauapp.domain.repository

import com.alifalpian.krakatauapp.domain.model.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthRepository {

    fun signInWithEmailAndPassword(email: String, password: String): Flow<Resource<AuthResult>>

    fun isUserLogged(): Flow<FirebaseUser?>

    fun signOut(): Flow<Resource<Unit>>

}