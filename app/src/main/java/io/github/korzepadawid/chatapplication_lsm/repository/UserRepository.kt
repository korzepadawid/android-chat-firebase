package io.github.korzepadawid.chatapplication_lsm.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.github.korzepadawid.chatapplication_lsm.model.User

class UserRepository {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDbRef: DatabaseReference = Firebase.database.reference
    private val _user: MutableLiveData<User> = MutableLiveData<User>()
}