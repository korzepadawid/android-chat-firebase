package io.github.korzepadawid.chatapplication_lsm.ui.auth

import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import io.github.korzepadawid.chatapplication_lsm.R

class RegisterActivity : BaseAuthActivity(R.layout.activity_register) {

    private lateinit var usernameEditText: EditText

    override fun initUi() {
        emailEditText = findViewById(R.id.edit_text_email_register)
        passwordEditText = findViewById(R.id.edit_text_password_register)
        usernameEditText = findViewById(R.id.edit_text_username_register)
        loginButton = findViewById(R.id.button_log_in_register)
        registerButton = findViewById(R.id.button_register_register)
    }

    override fun handleRegisterButton() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val username = usernameEditText.text.toString()

        try {
            authViewModel.register(email, password, username)
        } catch (e: RuntimeException) {
            Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun handleLogInButton() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}

