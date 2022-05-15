package io.github.korzepadawid.chatapplication_lsm.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.korzepadawid.chatapplication_lsm.repository.FirebaseAuthRepository

class AuthViewModelFactory(private val firebaseAuthRepository: FirebaseAuthRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(firebaseAuthRepository) as T
    }
}