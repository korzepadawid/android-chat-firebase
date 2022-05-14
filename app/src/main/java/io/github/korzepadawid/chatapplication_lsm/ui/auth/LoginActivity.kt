package io.github.korzepadawid.chatapplication_lsm.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.github.korzepadawid.chatapplication_lsm.R

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

        registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}