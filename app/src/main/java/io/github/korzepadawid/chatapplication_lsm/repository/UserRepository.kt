package io.github.korzepadawid.chatapplication_lsm.repository

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.github.korzepadawid.chatapplication_lsm.model.ThemePreference
import io.github.korzepadawid.chatapplication_lsm.model.User
import io.github.korzepadawid.chatapplication_lsm.util.Constants.FIREBASE_USERS_ROOT

class UserRepository {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDbRef: DatabaseReference = Firebase.database.reference
    private val _users: MutableLiveData<ArrayList<User>> = MutableLiveData<ArrayList<User>>()
    val users = _users as LiveData<ArrayList<User>>

    init {
        listenForUsers()
    }

    fun setThemeFromCurrentUserThemePreference() {
        getCurrentUserFromFirebaseDatabase()
            .addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(User::class.java)!!
                when (user.themePreference) {
                    ThemePreference.DARK -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                    ThemePreference.LIGHT -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }
            }
    }

    fun toggleUpdateAppThemeAndUserThemePreference() {
        getCurrentUserFromFirebaseDatabase()
            .addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(User::class.java)!!
                val userMap = user.toMap()
                when (user.themePreference) {
                    ThemePreference.DARK -> {
                        setLightTheme(userMap)
                    }
                    ThemePreference.LIGHT -> {
                        setDarkTheme(userMap)
                    }
                }
                snapshot.ref.updateChildren(userMap)
            }
    }

    private fun setDarkTheme(userMap: MutableMap<String, Any>) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        userMap["themePreference"] = ThemePreference.DARK
    }

    private fun setLightTheme(userMap: MutableMap<String, Any>) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        userMap["themePreference"] = ThemePreference.LIGHT
    }

    private fun getCurrentUserFromFirebaseDatabase() = mDbRef.child(FIREBASE_USERS_ROOT)
        .child(mAuth.uid.toString())
        .get()

    private fun listenForUsers() {
        mDbRef.child(FIREBASE_USERS_ROOT)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val fetchedUsers = ArrayList<User>()
                    snapshot.children.forEach { snapshotUser ->
                        val user = snapshotUser.getValue(User::class.java)!!
                        if (user.uid != mAuth.uid) {
                            fetchedUsers.add(user)
                        }
                    }
                    _users.postValue(fetchedUsers)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("firebase", error.message)
                }
            })
    }
}