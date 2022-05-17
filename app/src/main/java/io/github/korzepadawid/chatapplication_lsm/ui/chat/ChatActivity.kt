package io.github.korzepadawid.chatapplication_lsm.ui.chat

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.trimmedLength
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import io.github.korzepadawid.chatapplication_lsm.R
import io.github.korzepadawid.chatapplication_lsm.model.Place
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_UID
import io.github.korzepadawid.chatapplication_lsm.util.Constants.INTENT_RECEIVER_USERNAME

class ChatActivity : AppCompatActivity() {

    private lateinit var buttonSendMessage: Button
    private lateinit var editTextMessage: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val tempPlace: Place = Place(LatLng(52.53481, 17.58259))

        val mapFragment = supportFragmentManager.findFragmentById(
            R.id.map_fragment
        ) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            addGoogleMapMarker(googleMap, tempPlace)
            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                bounds.include(tempPlace.latLng)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
            }
        }

        val receiverUid = intent.getStringExtra(INTENT_RECEIVER_UID)
        val receiverUsername = intent.getStringExtra(INTENT_RECEIVER_USERNAME)

        supportActionBar?.title = receiverUsername

        Log.i("chat", "$receiverUsername ($receiverUid)")

        buttonSendMessage = findViewById(R.id.button_send_message)
        editTextMessage = findViewById(R.id.edit_text_message)

        disableButtonWhenEmptyMessageText()
    }

    private fun disableButtonWhenEmptyMessageText() {
        buttonSendMessage.isEnabled = false
        editTextMessage.addTextChangedListener { text ->
            buttonSendMessage.isEnabled = text != null && text.trimmedLength() > 0
        }
    }

    private fun addGoogleMapMarker(googleMap: GoogleMap, place: Place) {
        val position = MarkerOptions().position(place.latLng)
        googleMap.addMarker(position)
    }
}
