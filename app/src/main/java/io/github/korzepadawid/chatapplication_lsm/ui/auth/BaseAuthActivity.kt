package io.github.korzepadawid.chatapplication_lsm.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.korzepadawid.chatapplication_lsm.ui.contacts.ContactsActivity
import io.github.korzepadawid.chatapplication_lsm.util.Injection

abstract class BaseAuthActivity(@LayoutRes private val layoutRes: Int) : AppCompatActivity() {

    private lateinit var viewModelFactory: AuthViewModelFactory
    protected lateinit var authViewModel: AuthViewModel

    protected lateinit var emailEditText: EditText
    protected lateinit var passwordEditText: EditText
    protected lateinit var loginButton: Button
    protected lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        initUi();

        viewModelFactory = Injection.provideAuthViewModelFactory()
        authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        loginButton.setOnClickListener {
            handleLogInButton()
        }

        authViewModel.getAuthState().observe(this) { authState ->
            observeAuthState(authState)
        }

        registerButton.setOnClickListener {
            handleRegisterButton()
        }
    }

    private fun observeAuthState(authState: AuthState?) {
        if (AuthState.Success == authState) {
            val intent = Intent(this, ContactsActivity::class.java)
            finish()
            startActivity(intent)
        } else if (authState!!::class.java == AuthState.AuthError::class.java) {
            authState as AuthState.AuthError
            Toast.makeText(this, authState.message, Toast.LENGTH_SHORT).show()
        }
    }

    abstract fun initUi();

    abstract fun handleRegisterButton()

    abstract fun handleLogInButton()
}