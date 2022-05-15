package io.github.korzepadawid.chatapplication_lsm.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.ui.auth.AuthViewModel
import io.github.korzepadawid.chatapplication_lsm.ui.auth.AuthViewModelFactory
import io.github.korzepadawid.chatapplication_lsm.ui.auth.LoginActivity
import io.github.korzepadawid.chatapplication_lsm.util.Injection

class ContactsActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var authViewModelFactory: AuthViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        authViewModelFactory = Injection.provideAuthViewModelFactory()
        authViewModel = ViewModelProvider(this, authViewModelFactory)[AuthViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_logout -> handleLogOut()
        }
        return true
    }

    private fun handleLogOut() {
        authViewModel.logOut()
        val intent = Intent(this@ContactsActivity, LoginActivity::class.java)
        finish()
        startActivity(intent)
    }
}