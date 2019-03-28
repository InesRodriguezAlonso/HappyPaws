package com.happypaws.home.lostpets

import android.util.Log
import com.happypaws.res.RestApi
import com.happypaws.res.RestApiError
import com.happypaws.res.model.PetResponse
import rx.subscriptions.CompositeSubscription

class LostPetsOverviewPresenter(private val service: RestApi, private val view: LostPetsOverviewView) {
    private val subscriptions: CompositeSubscription = CompositeSubscription()

    fun getLostPetsList() {
        view.showResultList(false)

        val subscription = service.getLostPets(object : RestApi.LostPetsListCallback {
            override fun onSuccess(petListResponse: List<PetResponse>) {
                view.showLoadingSpinner(false)
                view.showResultList(petListResponse.isEmpty())
                view.showEmptyView(petListResponse.isEmpty())
                view.showErrorView(false)
                view.stopRefreshing()
                view.getLostPetsListSuccess(petListResponse)
            }

            override fun onError(networkError: RestApiError) {
                Log.e(TAG, networkError.message)
                view.showLoadingSpinner(false)
                view.showErrorView(true)
                view.showEmptyView(false)
                view.showResultList(true)
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

        var TAG = "LostPetsOverviewPresenter"
    }
}