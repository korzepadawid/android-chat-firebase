package io.github.korzepadawid.chatapplication_lsm.ui.contacts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.model.User
import io.github.korzepadawid.chatapplication_lsm.ui.auth.LoginActivity
import io.github.korzepadawid.chatapplication_lsm.util.Injection

class ContactsActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var users: ArrayList<User>
    private lateinit var userAdapter: UserAdapter

    private lateinit var contactsViewModelFactory: ContactsViewModelFactory
    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        contactsViewModelFactory = Injection.provideContactsViewModelFactory()
        contactsViewModel =
            ViewModelProvider(this, contactsViewModelFactory)[ContactsViewModel::class.java]
        contactsViewModel.setThemeFromCurrentUserThemePreference()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        userRecyclerView = findViewById(R.id.recycler_view_contacts)
        users = ArrayList()

        userAdapter = UserAdapter(this@ContactsActivity, users)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = userAdapter

        observeUsers()
    }

    private fun observeUsers() {
        contactsViewModel.getUsers().observe(this@ContactsActivity) {
            users.clear()
            users.addAll(it)
            userAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_logout -> handleLogOut()
            R.id.menu_item_set_theme -> {
                contactsViewModel.toggleUpdateAppThemeAndUserThemePreference()
            }
        }
        return true
    }

    private fun handleLogOut() {
        contactsViewModel.logOut()
        val intent = Intent(this@ContactsActivity, LoginActivity::class.java)
        finish()
        startActivity(intent)
    }
}