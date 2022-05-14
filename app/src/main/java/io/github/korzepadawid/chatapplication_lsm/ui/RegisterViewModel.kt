package io.github.korzepadawid.chatapplication_lsm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.korzepadawid.chatapplication_lsm.model.AuthState
import io.github.korzepadawid.chatapplication_lsm.repository.FirebaseAuthRepository

class RegisterViewModel(private val firebaseAuthRepository: FirebaseAuthRepository) : ViewModel() {

    fun register(email: String, password: String, username: String) =
        firebaseAuthRepository.register(email, password, username)

    fun getAuthState() = firebaseAuthRepository.authState
}