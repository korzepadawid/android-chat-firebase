package io.github.korzepadawid.chatapplication_lsm.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.model.Message
import io.github.korzepadawid.chatapplication_lsm.util.Constants.MESSAGE_RECEIVED
import io.github.korzepadawid.chatapplication_lsm.util.Constants.MESSAGE_SENT

class MessageAdapter(private val context: Context, private val messages: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MESSAGE_SENT) {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.layout_message_sent, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.layout_message_received, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messages[position]
        if (holder.javaClass == SentMessageViewHolder::class.java) {
            val viewHolder = holder as SentMessageViewHolder
            viewHolder.sentMessageTextView.text = currentMessage.text
        } else if (holder.javaClass == ReceivedMessageViewHolder::class.java) {
            val viewHolder = holder as ReceivedMessageViewHolder
            viewHolder.receivedMessageTextView.text = currentMessage.text
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messages[position]
        if (currentMessage.senderUid == FirebaseAuth.getInstance().uid) {
            return MESSAGE_SENT
        }
        return MESSAGE_RECEIVED
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessageTextView: TextView = itemView.findViewById(R.id.text_view_message_sent)
    }

    class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receivedMessageTextView: TextView =
            itemView.findViewById(R.id.text_view_message_received)
    }
}