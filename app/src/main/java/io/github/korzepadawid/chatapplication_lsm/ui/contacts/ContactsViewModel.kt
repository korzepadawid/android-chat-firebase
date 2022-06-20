package io.github.korzepadawid.chatapplication_lsm.ui.contacts

import androidx.lifecycle.ViewModel
import io.github.korzepadawid.chatapplication_lsm.repository.FirebaseAuthRepository
import io.github.korzepadawid.chatapplication_lsm.repository.UserRepository

class ContactsViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    fun setThemeFromCurrentUserThemePreference() =
        userRepository.setThemeFromCurrentUserThemePreference()

    fun toggleUpdateAppThemeAndUserThemePreference() =
        userRepository.toggleUpdateAppThemeAndUserThemePreference()

    fun getUsers() = userRepository.users

    fun logOut() = firebaseAuthRepository.logOut()
}