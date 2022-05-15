package io.github.korzepadawid.chatapplication_lsm.model

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val themePreference: ThemePreference = ThemePreference.LIGHT
)
