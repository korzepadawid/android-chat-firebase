package io.github.korzepadawid.chatapplication_lsm.model

class Message(
    val text: String = "",
    val senderUid: String = "",
    val receiverUid: String = "",
    val type: Type = Type.TEXT,
    val timestamp: String = System.currentTimeMillis().toString()
) {

    enum class Type {
        LOCATION,
        TEXT
    }
}