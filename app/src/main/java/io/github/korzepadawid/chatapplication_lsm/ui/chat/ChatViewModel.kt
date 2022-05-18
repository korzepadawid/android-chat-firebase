package io.github.korzepadawid.chatapplication_lsm.ui.chat

import androidx.lifecycle.ViewModel
import io.github.korzepadawid.chatapplication_lsm.model.Message
import io.github.korzepadawid.chatapplication_lsm.model.Place
import io.github.korzepadawid.chatapplication_lsm.repository.MessageRepository

class ChatViewModel(private val messageRepository: MessageRepository) : ViewModel() {

    fun sendMessage(text: String, receiverUid: String, place: Place? = null) =
        messageRepository.sendMessage(text, receiverUid, place)

    fun getMessages() = messageRepository.messages

    fun getMessagesByReceiverUid(receiverUid: String) =
        messageRepository.getMessagesByReceiverUid(receiverUid)
}