package io.github.korzepadawid.chatapplication_lsm.ui.auth

import androidx.lifecycle.ViewModel
import io.github.korzepadawid.chatapplication_lsm.repository.FirebaseAuthRepository

class AuthViewModel(private val firebaseAuthRepository: FirebaseAuthRepository) : ViewModel() {

    fun register(email: String, password: String, username: String) =
        firebaseAuthRepository.register(email, password, username)

    fun login(email: String, password: String) = firebaseAuthRepository.login(email, password)

    fun logOut() = firebaseAuthRepository.logOut()

    fun getAuthState() = firebaseAuthRepository.authState
}