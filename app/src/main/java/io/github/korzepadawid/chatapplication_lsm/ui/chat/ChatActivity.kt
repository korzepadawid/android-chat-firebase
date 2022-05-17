package io.github.korzepadawid.chatapplication_lsm.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.util.Constants
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_UID
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_USERNAME

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val receiverUid = intent.getStringExtra(INTENT_RECEIVER_UID)
        val receiverUsername = intent.getStringExtra(INTENT_RECEIVER_USERNAME)

        Log.i("chat", "$receiverUsername ($receiverUid)")
    }
}