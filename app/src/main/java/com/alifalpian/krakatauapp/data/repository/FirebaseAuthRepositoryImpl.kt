package com.alifalpian.krakatauapp.data.repository

import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {

    override fun signInWithEmailAndPassword(email: String, password: String): Flow<Resource<AuthResult>> = flow<Resource<AuthResult>> {
        emit(Resource.Loading)
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(Resource.Success(result))
    }.catch {
        emit(Resource.Error(it.message))
    }

    override fun isUserLogged(): Flow<FirebaseUser?> = flow<FirebaseUser?> {
        val result = firebaseAuth.currentUser
        emit(result)
    }

    override fun signOut(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        val result = firebaseAuth.signOut()
        emit(Resource.Success(result))
    }
}