package io.github.korzepadawid.chatapplication_lsm.ui.chat

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.model.Message
import io.github.korzepadawid.chatapplication_lsm.ui.map.GoogleMapsActivity
import io.github.korzepadawid.chatapplication_lsm.util.Constants.GEOLOCATION_RECEIVED
import io.github.korzepadawid.chatapplication_lsm.util.Constants.GEOLOCATION_SENT
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_LATITUDE
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_LONGITUDE
import io.github.korzepadawid.chatapplication_lsm.util.Constants.MESSAGE_RECEIVED
import io.github.korzepadawid.chatapplication_lsm.util.Constants.MESSAGE_SENT
import io.github.korzepadawid.chatapplication_lsm.util.Constants.PIN_EMOJI
import io.github.korzepadawid.chatapplication_lsm.util.Constants.WORLD_EMOJI

class MessageAdapter(private val context: Context, private val messages: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MESSAGE_SENT || viewType == GEOLOCATION_SENT) {
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
            viewHolder.sentMessageTextView.text = getMessageContent(currentMessage)
        } else if (holder.javaClass == ReceivedMessageViewHolder::class.java) {
            val viewHolder = holder as ReceivedMessageViewHolder
            viewHolder.receivedMessageTextView.text = getMessageContent(currentMessage)
        }

        holder.itemView.setOnLongClickListener {
            Log.i("long", "long pres")
            true
        }

        if (Message.Type.LOCATION == currentMessage.type) {
            holder.itemView.setOnClickListener {
                val intent = Intent(context, GoogleMapsActivity::class.java);
                intent.putExtra(INTENT_LATITUDE, currentMessage.place!!.latitude)
                intent.putExtra(INTENT_LONGITUDE, currentMessage.place.longitude)
                context.startActivity(intent)
            }
        }
    }

    private fun getMessageContent(currentMessage: Message) =
        if (Message.Type.LOCATION == currentMessage.type) "$WORLD_EMOJI$PIN_EMOJI" else currentMessage.text

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messages[position]
        return when (currentMessage.type) {
            Message.Type.LOCATION -> if (currentMessage.senderUid == FirebaseAuth.getInstance().uid) {
                GEOLOCATION_SENT
            } else {
                GEOLOCATION_RECEIVED
            }
            Message.Type.TEXT -> if (currentMessage.senderUid == FirebaseAuth.getInstance().uid) {
                MESSAGE_SENT
            } else {
                MESSAGE_RECEIVED
            }
        }
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