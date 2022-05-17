package io.github.korzepadawid.chatapplication_lsm.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.korzepadawid.chatapplication_lsm.repository.MessageRepository

class ChatViewModelFactory(
    private val messageRepository: MessageRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(messageRepository) as T
    }
}