package io.github.korzepadawid.chatapplication_lsm.util

import io.github.korzepadawid.chatapplication_lsm.repository.FirebaseAuthRepository
import io.github.korzepadawid.chatapplication_lsm.ui.RegisterViewModelFactory

object Injection {

    fun provideRegisterViewModelFactory(): RegisterViewModelFactory {
        return RegisterViewModelFactory(FirebaseAuthRepository())
    }
}