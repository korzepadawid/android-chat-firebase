package io.github.korzepadawid.chatapplication_lsm.model

data class User(
    private val uid: String,
    private val username: String,
    private val themePreference: ThemePreference
)
