package io.github.korzepadawid.chatapplication_lsm.model

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val themePreference: ThemePreference = ThemePreference.LIGHT
) {
    fun toMap(): MutableMap<String, Any>  = mutableMapOf(
        "email" to email,
        "themePreference" to themePreference,
        "uid" to uid,
        "username" to username,
    )
}
