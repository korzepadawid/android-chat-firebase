package io.github.korzepadawid.chatapplication_lsm.ui.chat

import androidx.lifecycle.ViewModel
import io.github.korzepadawid.chatapplication_lsm.model.Message
import io.github.korzepadawid.chatapplication_lsm.repository.MessageRepository

class ChatViewModel(private val messageRepository: MessageRepository) : ViewModel() {

    fun sendMessage(text: String, receiverUid: String, type: Message.Type) =
        messageRepository.sendMessage(text, receiverUid, type)
}