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

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeUi()
    }

    private fun initializeUi() {
        emailEditText = findViewById(R.id.edit_text_email_login)
        passwordEditText = findViewById(R.id.edit_text_password_login)
        loginButton = findViewById(R.id.button_log_in_login)
        registerButton = findViewById(R.id.button_register_login)

        val viewModelFactory = Injection.provideAuthViewModelFactory()
        val authViewModel =
            ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]


        registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        authViewModel.getAuthState().observe(this) { authState ->
            if (AuthState.Success == authState) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                finish()
                startActivity(intent)
            } else if (authState::class.java == AuthState.AuthError::class.java) {
                authState as AuthState.AuthError
                Toast.makeText(this@LoginActivity, authState.message, Toast.LENGTH_SHORT).show()
            }
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            try {
                authViewModel.login(email, password)
            } catch (e: RuntimeException) {
                Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}