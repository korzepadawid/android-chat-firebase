package io.github.korzepadawid.chatapplication_lsm.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.korzepadawid.chatapplication_lsm.repository.FirebaseAuthRepository
import io.github.korzepadawid.chatapplication_lsm.repository.UserRepository

class ContactsViewModelFactory(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactsViewModel(firebaseAuthRepository, userRepository) as T
    }

}