package io.github.korzepadawid.chatapplication_lsm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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