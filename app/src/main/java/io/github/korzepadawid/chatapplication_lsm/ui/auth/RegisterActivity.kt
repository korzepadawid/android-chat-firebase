package io.github.korzepadawid.chatapplication_lsm.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.github.korzepadawid.chatapplication_lsm.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initializeUi()
    }

    private fun initializeUi() {
        emailEditText = findViewById(R.id.edit_text_email_register)
        passwordEditText = findViewById(R.id.edit_text_password_register)
        loginButton = findViewById(R.id.button_log_in_register)
        registerButton = findViewById(R.id.button_register_register)

        loginButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}