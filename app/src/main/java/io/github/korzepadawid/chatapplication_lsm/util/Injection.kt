package io.github.korzepadawid.chatapplication_lsm.util

import io.github.korzepadawid.chatapplication_lsm.repository.FirebaseAuthRepository
import io.github.korzepadawid.chatapplication_lsm.repository.UserRepository
import io.github.korzepadawid.chatapplication_lsm.ui.auth.AuthViewModelFactory
import io.github.korzepadawid.chatapplication_lsm.ui.contacts.ContactsViewModelFactory

object Injection {

    fun provideAuthViewModelFactory(): AuthViewModelFactory {
        return AuthViewModelFactory(FirebaseAuthRepository())
    }

    fun provideContactsViewModelFactory(): ContactsViewModelFactory {
        return ContactsViewModelFactory(FirebaseAuthRepository(), UserRepository())
    }
}