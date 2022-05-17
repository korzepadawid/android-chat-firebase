package io.github.korzepadawid.chatapplication_lsm.ui.chat

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.trimmedLength
import androidx.core.widget.addTextChangedListener
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_UID
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_USERNAME

class ChatActivity : AppCompatActivity() {

    private lateinit var buttonSendMessage: Button
    private lateinit var editTextMessage: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val receiverUid = intent.getStringExtra(INTENT_RECEIVER_UID)
        val receiverUsername = intent.getStringExtra(INTENT_RECEIVER_USERNAME)

        supportActionBar?.title = receiverUsername

        Log.i("chat", "$receiverUsername ($receiverUid)")

        buttonSendMessage = findViewById(R.id.button_send_message)
        editTextMessage = findViewById(R.id.edit_text_message)

        disableButtonWhenEmptyMessageText()
    }

    private fun disableButtonWhenEmptyMessageText() {
        buttonSendMessage.isEnabled = false
        editTextMessage.addTextChangedListener { text ->
            buttonSendMessage.isEnabled = text != null && text.trimmedLength() > 0
        }
    }
}