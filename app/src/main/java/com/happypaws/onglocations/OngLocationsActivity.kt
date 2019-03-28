package com.happypaws.onglocations

import android.os.Bundle
import android.util.Log
import butterknife.ButterKnife
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.happypaws.R
import com.happypaws.base.BaseActivity
import com.happypaws.res.RestApi
import com.happypaws.res.model.LocationResponse
import com.happypaws.utils.DialogUtils
import javax.inject.Inject

class OngLocationsActivity : BaseActivity(), OnMapReadyCallback, OngLocationsView {

    @set:Inject
    internal var service: RestApi? = null

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        dependencies!!.inject(this)

        setContentView(R.layout.activity_ong_locations)
        ButterKnife.bind(this)

        initActionBar()
        initMap()
    }

    private fun initMap() {
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(TAG, "onMapReady")

        this.googleMap = googleMap

        val presenter = OngLocationsPresenter(
                service,
                this
        )
        presenter.getOngLocationsList()
    }

    override fun getOngLocationsListSuccess(lostPetsListResponse: List<LocationResponse>) {
        var lasLatLng = LatLng(0.0, 0.0)
        for (locationResponse in lostPetsListResponse) {
            val latLng = LatLng(locationResponse.latitude!!, locationResponse.longitude!!)
            googleMap!!.addMarker(MarkerOptions().position(latLng).title(locationResponse.address))
            lasLatLng = latLng
        }
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(lasLatLng))
    }

    override fun onFailure(errorMessage: String) {
        Log.e(TAG, getString(R.string.on_error_response, errorMessage))

        DialogUtils.showDialogAlert(this, errorMessage)
    }

    private fun initActionBar() {
        supportActionBar!!.title = getString(R.string.ong_locations_activity_title)
    }

    companion object {

        var TAG = "OngLocationsActivity"
    }
}