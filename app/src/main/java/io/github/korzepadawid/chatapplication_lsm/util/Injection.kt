package io.github.korzepadawid.chatapplication_lsm.util

import io.github.korzepadawid.chatapplication_lsm.repository.FirebaseAuthRepository
import io.github.korzepadawid.chatapplication_lsm.ui.auth.AuthViewModelFactory

object Injection {

    fun provideAuthViewModelFactory(): AuthViewModelFactory {
        return AuthViewModelFactory(FirebaseAuthRepository())
    }
}