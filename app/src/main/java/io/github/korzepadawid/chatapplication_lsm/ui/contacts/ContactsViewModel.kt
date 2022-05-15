package io.github.korzepadawid.chatapplication_lsm.ui.contacts

import androidx.lifecycle.ViewModel
import io.github.korzepadawid.chatapplication_lsm.repository.FirebaseAuthRepository
import io.github.korzepadawid.chatapplication_lsm.repository.UserRepository

class ContactsViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    fun logOut() = firebaseAuthRepository.logOut()
}