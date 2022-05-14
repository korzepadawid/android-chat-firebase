package io.github.korzepadawid.chatapplication_lsm.model

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    class AuthError(val message: String? = null) : AuthState()

}
