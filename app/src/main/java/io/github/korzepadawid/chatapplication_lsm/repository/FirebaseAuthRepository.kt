package io.github.korzepadawid.chatapplication_lsm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.github.korzepadawid.chatapplication_lsm.model.User
import io.github.korzepadawid.chatapplication_lsm.ui.auth.AuthState
import io.github.korzepadawid.chatapplication_lsm.util.Constants.FIREBASE_USERS_ROOT

class FirebaseAuthRepository {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDbRef: DatabaseReference = Firebase.database.reference
    private val _authState: MutableLiveData<AuthState> = MutableLiveData<AuthState>()
    val authState = _authState as LiveData<AuthState>

    fun register(email: String, password: String, username: String) {
        _authState.postValue(AuthState.Loading)

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(uid = mAuth.uid.toString(), username = username, email = email)
                    saveUser(user)
                    _authState.postValue(AuthState.Success)
                } else {
                    _authState.postValue(AuthState.AuthError("Can't create account."))
                }
            }
        _authState.postValue(AuthState.Idle)
    }

    fun login(email: String, password: String) {
        _authState.postValue(AuthState.Loading)

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.postValue(AuthState.Success)
                } else {
                    _authState.postValue(AuthState.AuthError("Invalid credentials."))
                }
            }
        _authState.postValue(AuthState.Idle)
    }

    fun logOut() = mAuth.signOut()


    private fun saveUser(user: User) {
        mDbRef.child(FIREBASE_USERS_ROOT)
            .child(user.uid)
            .setValue(user)
    }
}