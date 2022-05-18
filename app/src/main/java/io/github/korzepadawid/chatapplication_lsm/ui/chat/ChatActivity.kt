package io.github.korzepadawid.chatapplication_lsm.ui.chat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.text.trimmedLength
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.model.Message
import io.github.korzepadawid.chatapplication_lsm.model.Place
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_UID
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_USERNAME
import io.github.korzepadawid.chatapplication_lsm.util.Injection
import io.github.korzepadawid.chatapplication_lsm.util.LocationUtility
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class ChatActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var buttonSendMessage: Button
    private lateinit var buttonSendGeolocation: Button
    private lateinit var editTextMessage: EditText
    private lateinit var recyclerViewMessages: RecyclerView

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var chatViewModelFactory: ChatViewModelFactory
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatViewModelFactory = Injection.provideChatViewModelFactory()
        chatViewModel = ViewModelProvider(this, chatViewModelFactory)[ChatViewModel::class.java]

        val receiverUid = intent.getStringExtra(INTENT_RECEIVER_UID)!!
        val receiverUsername = intent.getStringExtra(INTENT_RECEIVER_USERNAME)!!

        supportActionBar?.title = receiverUsername

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        recyclerViewMessages = findViewById(R.id.recycler_view_messages)
        chatViewModel.getMessagesByReceiverUid(receiverUid)
        recyclerViewMessages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        val messages = ArrayList<Message>()
        messageAdapter = MessageAdapter(this, messages)
        recyclerViewMessages.adapter = messageAdapter

        buttonSendMessage = findViewById(R.id.button_send_message)
        editTextMessage = findViewById(R.id.edit_text_message)
        buttonSendGeolocation = findViewById(R.id.button_send_geolocation)

        chatViewModel.getMessages().observe(this@ChatActivity) {
            messages.clear()
            messages.addAll(it)
            messageAdapter.notifyDataSetChanged()
            recyclerViewMessages.scrollToPosition(messages.size - 1)
        }

        disableButtonWhenEmptyMessageText()

        listenForSendingMessage(receiverUid)

        listenForSendingGeolocation(receiverUid)
    }

    private fun listenForSendingGeolocation(receiverUid: String?) {
        buttonSendGeolocation.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions()
            }
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                val place = Place(latitude = it.latitude, longitude = it.longitude)
                chatViewModel.sendMessage("", receiverUid.toString(), place)
            }
        }
    }

    private fun listenForSendingMessage(receiverUid: String?) {
        buttonSendMessage.setOnClickListener {
            val text = editTextMessage.text.toString()
            chatViewModel.sendMessage(text, receiverUid.toString())
            editTextMessage.setText("")
        }
    }

    private fun disableButtonWhenEmptyMessageText() {
        buttonSendMessage.isEnabled = false
        editTextMessage.addTextChangedListener { text ->
            buttonSendMessage.isEnabled = text != null && text.trimmedLength() > 0
        }
    }

    companion object {
        const val REQUEST_CODE_LOCATION_PERMISSION = 100
    }

    private fun requestPermissions() {
        if (LocationUtility.hasLocationPermissions(this)) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION

            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.i("Permissions", "Granted")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }
}
