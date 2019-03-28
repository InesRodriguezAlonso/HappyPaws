package com.happypaws.home.adoption

import android.util.Log
import com.happypaws.res.RestApi
import com.happypaws.res.RestApiError
import com.happypaws.res.model.PetResponse
import rx.subscriptions.CompositeSubscription

class AdoptionOverviewPresenter(private val service: RestApi?, private val view: AdoptionOverviewView) {
    private val subscriptions: CompositeSubscription = CompositeSubscription()

    fun getPetsForAdoptionList() {
        val subscription = service!!.getPetsForAdoption(object : RestApi.PetsForAdoptionListCallback {
            override fun onSuccess(cityListResponse: List<PetResponse>) {
                view.showLoadingSpinner(false)
                view.showResultList(cityListResponse.isEmpty())
                view.showEmptyView(cityListResponse.isEmpty())
                view.showErrorView(false)
                view.stopRefreshing()
                view.getPetsForAdoptionListSuccess(cityListResponse)
            }

            override fun onError(networkError: RestApiError) {
                Log.e(TAG, networkError.message)
                view.showLoadingSpinner(false)
                view.showResultList(true)
                view.showErrorView(true)
                view.showEmptyView(false)
                view.stopRefreshing()
                view.onFailure(networkError.appErrorMessage)
            }
        })

        subscriptions.add(subscription)
    }

    fun onStop() {
        subscriptions.unsubscribe()
    }

    companion object {

        var TAG = "AdoptionOverviewPresenter"
    }
}