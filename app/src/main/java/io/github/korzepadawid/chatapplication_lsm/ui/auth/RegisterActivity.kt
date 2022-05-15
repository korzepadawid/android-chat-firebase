package io.github.korzepadawid.chatapplication_lsm.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.model.AuthState
import io.github.korzepadawid.chatapplication_lsm.ui.MainActivity
import io.github.korzepadawid.chatapplication_lsm.util.Injection

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var usernameEditText: EditText

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initializeUi()
    }

    private fun initializeUi() {
        val viewModelFactory = Injection.provideAuthViewModelFactory()
        val authViewModel =
            ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        emailEditText = findViewById(R.id.edit_text_email_register)
        passwordEditText = findViewById(R.id.edit_text_password_register)
        usernameEditText = findViewById(R.id.edit_text_username_register)
        loginButton = findViewById(R.id.button_log_in_register)
        registerButton = findViewById(R.id.button_register_register)

        loginButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        authViewModel.getAuthState().observe(this) { authState ->
            if (AuthState.Success == authState) {
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                finish()
                startActivity(intent)
            } else if (authState::class.java == AuthState.AuthError::class.java) {
                authState as AuthState.AuthError
                Toast.makeText(this@RegisterActivity, authState.message, Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val username = usernameEditText.text.toString()

            try {
                authViewModel.register(email, password, username)
            } catch (e: RuntimeException) {
                Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

