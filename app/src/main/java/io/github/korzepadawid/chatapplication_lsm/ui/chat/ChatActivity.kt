package io.github.korzepadawid.chatapplication_lsm.ui.chat

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.trimmedLength
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.model.Message
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_UID
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_USERNAME
import io.github.korzepadawid.chatapplication_lsm.util.Injection

class ChatActivity : AppCompatActivity() {

    private lateinit var buttonSendMessage: Button
    private lateinit var editTextMessage: EditText

    private lateinit var chatViewModelFactory: ChatViewModelFactory
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatViewModelFactory = Injection.provideChatViewModelFactory()
        chatViewModel = ViewModelProvider(this, chatViewModelFactory)[ChatViewModel::class.java]


        val receiverUid = intent.getStringExtra(INTENT_RECEIVER_UID)
        val receiverUsername = intent.getStringExtra(INTENT_RECEIVER_USERNAME)

        supportActionBar?.title = receiverUsername

        Log.i("chat", "$receiverUsername ($receiverUid)")

        buttonSendMessage = findViewById(R.id.button_send_message)
        editTextMessage = findViewById(R.id.edit_text_message)

        disableButtonWhenEmptyMessageText()

        listenForSendingMessage(receiverUid)
    }

    private fun listenForSendingMessage(receiverUid: String?) {
        buttonSendMessage.setOnClickListener {
            val text = editTextMessage.text.toString()
            chatViewModel.sendMessage(text, receiverUid.toString(), Message.Type.TEXT)
            editTextMessage.setText("")
        }
    }

    private fun disableButtonWhenEmptyMessageText() {
        buttonSendMessage.isEnabled = false
        editTextMessage.addTextChangedListener { text ->
            buttonSendMessage.isEnabled = text != null && text.trimmedLength() > 0
        }
    }
}
