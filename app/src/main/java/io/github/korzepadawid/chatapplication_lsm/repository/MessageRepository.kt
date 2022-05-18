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
import io.github.korzepadawid.chatapplication_lsm.model.Message
import io.github.korzepadawid.chatapplication_lsm.model.Place
import io.github.korzepadawid.chatapplication_lsm.util.Constants.FIREBASE_CHAT_ROOMS_ROOT
import io.github.korzepadawid.chatapplication_lsm.util.Constants.FIREBASE_MESSAGES_ROOT

class MessageRepository {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDbRef: DatabaseReference = Firebase.database.reference
    private val _messages = MutableLiveData<ArrayList<Message>>()
    val messages = _messages as LiveData<ArrayList<Message>>

    fun sendMessage(text: String, receiverUid: String, place: Place? = null) {
        val senderUid = mAuth.currentUser?.uid.toString()
        val (senderChatRoomUid, receiverChatRoomUid) = parseUids(senderUid, receiverUid)

        val type: Message.Type =
            if (place != null) Message.Type.LOCATION else Message.Type.TEXT

        val message = Message(text, senderUid, receiverUid, type, place)

        mDbRef.child(FIREBASE_CHAT_ROOMS_ROOT)
            .child(senderChatRoomUid)
            .child(FIREBASE_MESSAGES_ROOT)
            .push()
            .setValue(message)
            .addOnSuccessListener {
                mDbRef.child(FIREBASE_CHAT_ROOMS_ROOT)
                    .child(receiverChatRoomUid)
                    .child(FIREBASE_MESSAGES_ROOT)
                    .push()
                    .setValue(message)
                    .addOnSuccessListener {
                        Log.i("chat", "Message from $senderUid to $receiverUid has been sent.")
                    }
            }
    }

    fun getMessagesByReceiverUid(receiverUid: String) {
        val senderUid = mAuth.currentUser?.uid.toString()
        val (senderChatRoomUid) = parseUids(senderUid, receiverUid)
        observeMessageChanges(senderChatRoomUid)
    }

    private fun observeMessageChanges(senderChatRoomUid: String) {
        mDbRef.child(FIREBASE_CHAT_ROOMS_ROOT)
            .child(senderChatRoomUid)
            .child(FIREBASE_MESSAGES_ROOT)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val snapshotMessages = ArrayList<Message>()
                    snapshot.children.forEach { messageSnapshot ->
                        val message = messageSnapshot.getValue(Message::class.java)!!
                        snapshotMessages.add(message)
                    }
                    _messages.postValue(snapshotMessages)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("firebase", error.message)
                }
            })
    }

    private fun parseUids(
        senderUid: String,
        receiverUid: String
    ): Pair<String, String> {
        val senderChatRoomUid = senderUid + receiverUid
        val receiverChatRoomUid = receiverUid + senderUid
        return Pair(senderChatRoomUid, receiverChatRoomUid)
    }
}