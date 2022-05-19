package io.github.korzepadawid.chatapplication_lsm.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import io.github.korzepadawid.chatapplication_lsm.R

class GoogleMapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)
        val locationFromIntent = getLocation()
        val mapFragment = supportFragmentManager.findFragmentById(
            R.id.map_fragment
        ) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                bounds.include(locationFromIntent)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bounds.build().center, 13F))
            }
            addMarkers(googleMap, locationFromIntent)
        }
    }

    private fun getLocation(): LatLng {
        return LatLng(52.5348100, 17.5825900)
    }

    private fun addMarkers(googleMap: GoogleMap, latLng: LatLng) {
        googleMap.addMarker(
            MarkerOptions().title("Your friend's location.").position(latLng)
        )
    }
}