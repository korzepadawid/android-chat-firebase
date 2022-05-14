package io.github.korzepadawid.chatapplication_lsm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.github.korzepadawid.chatapplication_lsm.model.User
import io.github.korzepadawid.chatapplication_lsm.util.Constants.FIREBASE_USERS_ROOT

class FirebaseAuthRepository {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDbRef: DatabaseReference = Firebase.database.reference
    private val _user: MutableLiveData<User> = MutableLiveData<User>()
    val user = _user as LiveData<User>

    fun register(email: String, password: String, username: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(uid = mAuth.uid.toString(), username = username, email = email)
                    saveUser(user)
                    _user.postValue(user)
                } else {
                    throw RuntimeException("Registration error: can't create user $email")
                }
            }
    }

    private fun saveUser(user: User) {
        mDbRef.child(FIREBASE_USERS_ROOT)
            .child(user.uid)
            .setValue(user)
    }
}