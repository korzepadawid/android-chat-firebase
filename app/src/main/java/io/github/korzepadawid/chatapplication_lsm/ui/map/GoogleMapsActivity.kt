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
import io.github.korzepadawid.chatapplication_lsm.util.Constants

class GoogleMapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        val locationFromIntent = getLocationFromIntent()

        val mapFragment = supportFragmentManager.findFragmentById(
            R.id.map_fragment
        ) as? SupportMapFragment

        printMapMarkers(mapFragment, locationFromIntent)
    }

    private fun printMapMarkers(
        mapFragment: SupportMapFragment?,
        locationFromIntent: LatLng
    ) {
        mapFragment?.getMapAsync { googleMap ->
            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                bounds.include(locationFromIntent)
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        bounds.build().center,
                        13F
                    )
                )
            }
            addMarkers(googleMap, locationFromIntent)
        }
    }

    private fun getLocationFromIntent(): LatLng {
        val latitude = intent.getDoubleExtra(Constants.INTENT_LATITUDE, 0.0)
        val longitude = intent.getDoubleExtra(Constants.INTENT_LONGITUDE, 0.0)
        return LatLng(latitude, longitude)
    }

    private fun addMarkers(googleMap: GoogleMap, latLng: LatLng) {
        googleMap.addMarker(
            MarkerOptions().title("${latLng.latitude}° ${latLng.longitude}°").position(latLng)
        )
    }
}