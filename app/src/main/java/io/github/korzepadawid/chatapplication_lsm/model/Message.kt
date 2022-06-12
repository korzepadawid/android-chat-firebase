package io.github.korzepadawid.chatapplication_lsm.model

import java.util.*

class Message(
    val uid: String = UUID.randomUUID().toString(),
    val text: String = "",
    val senderUid: String = "",
    val receiverUid: String = "",
    val type: Type = Type.TEXT,
    val place: Place? = null,
    val timestamp: String = System.currentTimeMillis().toString()
) {

    enum class Type {
        LOCATION,
        TEXT
    }
}