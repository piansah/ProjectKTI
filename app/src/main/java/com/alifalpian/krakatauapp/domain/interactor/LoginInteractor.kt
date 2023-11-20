package com.alifalpian.krakatauapp.domain.interactor

import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.User
import com.alifalpian.krakatauapp.domain.repository.FirebaseAuthRepository
import com.alifalpian.krakatauapp.domain.repository.FirebaseFirestoreRepository
import com.alifalpian.krakatauapp.domain.usecase.LoginUseCase
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginInteractor @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository
) : LoginUseCase {

    override fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> {
        return firebaseAuthRepository.signInWithEmailAndPassword(email, password)
    }

    override fun getUser(uid: String): Flow<Resource<User>> {
        return firebaseFirestoreRepository.getUser(uid)
    }

    override fun signOut(): Flow<Resource<Unit>> {
        return firebaseAuthRepository.signOut()
    }
}