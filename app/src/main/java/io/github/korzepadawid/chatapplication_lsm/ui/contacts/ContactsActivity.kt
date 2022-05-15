package io.github.korzepadawid.chatapplication_lsm.ui.contacts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.lifecycle.ViewModelProvider
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.ui.auth.LoginActivity
import io.github.korzepadawid.chatapplication_lsm.util.Injection

class ContactsActivity : AppCompatActivity() {

    private lateinit var contactsViewModelFactory: ContactsViewModelFactory
    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        contactsViewModelFactory = Injection.provideContactsViewModelFactory()
        contactsViewModel =
            ViewModelProvider(this, contactsViewModelFactory)[ContactsViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_logout -> handleLogOut()
            R.id.menu_item_set_theme -> {
                Log.i("menu", "set theme")
            }
        }
        return true
    }

    private fun handleLogOut() {
        contactsViewModel.logOut()
        val intent = Intent(this@ContactsActivity, LoginActivity::class.java)
        finish()
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        startActivity(intent)
    }
}