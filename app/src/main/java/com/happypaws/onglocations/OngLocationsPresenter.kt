package com.happypaws.onglocations

import android.util.Log
import com.happypaws.res.RestApi
import com.happypaws.res.RestApiError
import com.happypaws.res.model.LocationResponse
import rx.subscriptions.CompositeSubscription

class OngLocationsPresenter(private val service: RestApi?, private val view: OngLocationsView) {
    private val subscriptions: CompositeSubscription = CompositeSubscription()

    fun getOngLocationsList() {
        val subscription = service!!.geOngLocations(object : RestApi.OngLocationsListCallback {
            override fun onSuccess(locationListResponse: List<LocationResponse>) {
                view.getOngLocationsListSuccess(locationListResponse)
            }

            override fun onError(networkError: RestApiError) {
                Log.e(TAG, networkError.message)
                view.onFailure(networkError.appErrorMessage)
            }
        })

        subscriptions.add(subscription)
    }

    fun onStop() {
        subscriptions.unsubscribe()
    }

    companion object {

        var TAG = "OngLocationsPresenter"
    }
}