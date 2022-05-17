package io.github.korzepadawid.chatapplication_lsm.ui.contacts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.model.User
import io.github.korzepadawid.chatapplication_lsm.ui.chat.ChatActivity
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_UID
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_USERNAME

class UserAdapter(private val context: Context, private val users: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_contact, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = users[position]
        holder.usernameTextView.text = currentUser.username
        holder.emailTextView.text = currentUser.email

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(INTENT_RECEIVER_UID, currentUser.uid)
            intent.putExtra(INTENT_RECEIVER_USERNAME, currentUser.username)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.text_view_username)
        val emailTextView: TextView = itemView.findViewById(R.id.text_view_email)
    }
}