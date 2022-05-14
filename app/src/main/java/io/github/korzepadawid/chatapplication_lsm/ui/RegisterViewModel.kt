package io.github.korzepadawid.chatapplication_lsm.ui

import androidx.lifecycle.ViewModel
import io.github.korzepadawid.chatapplication_lsm.repository.FirebaseAuthRepository

class RegisterViewModel(private val firebaseAuthRepository: FirebaseAuthRepository) : ViewModel() {

    fun register(email: String, password: String, username: String) =
        firebaseAuthRepository.register(email, password, username)
}