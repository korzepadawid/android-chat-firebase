package io.github.korzepadawid.chatapplication_lsm.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.github.korzepadawid.chatapplication_lsm.model.Message
import io.github.korzepadawid.chatapplication_lsm.model.Place
import io.github.korzepadawid.chatapplication_lsm.util.Constants.FIREBASE_CHAT_ROOMS_ROOT
import io.github.korzepadawid.chatapplication_lsm.util.Constants.FIREBASE_MESSAGES_ROOT

class MessageRepository {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDbRef: DatabaseReference = Firebase.database.reference

    fun sendMessage(text: String, receiverUid: String, place: Place? = null) {
        val senderUid = mAuth.currentUser?.uid.toString()
        val senderChatRoomUid = senderUid + receiverUid
        val receiverChatRoomUid = receiverUid + senderUid

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
}