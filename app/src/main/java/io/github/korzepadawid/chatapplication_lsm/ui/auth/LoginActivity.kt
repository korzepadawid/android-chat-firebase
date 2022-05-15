package io.github.korzepadawid.chatapplication_lsm.ui.auth

import android.content.Intent
import android.widget.Toast
import io.github.korzepadawid.chatapplication_lsm.R

class LoginActivity : BaseAuthActivity(R.layout.activity_login) {

    override fun initUi() {
        emailEditText = findViewById(R.id.edit_text_email_login)
        passwordEditText = findViewById(R.id.edit_text_password_login)
        loginButton = findViewById(R.id.button_log_in_login)
        registerButton = findViewById(R.id.button_register_login)
    }

    override fun handleRegisterButton() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun handleLogInButton() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        try {
            authViewModel.login(email, password)
        } catch (e: RuntimeException) {
            Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}