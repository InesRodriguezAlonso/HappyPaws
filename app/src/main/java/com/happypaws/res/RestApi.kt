package com.happypaws.res

import com.happypaws.res.model.AdoptionRequest
import com.happypaws.res.model.LocationResponse
import com.happypaws.res.model.PetResponse
import retrofit2.Response
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class RestApi(val service: RestApiService) {

    fun getLostPets(callback: LostPetsListCallback): Subscription {
        return service.lostPets
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext{ Observable.error(it) }
                .subscribe(object : Subscriber<List<PetResponse>>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable) {
                        callback.onError(RestApiError(e))
                    }

                    override fun onNext(response: List<PetResponse>) {
                        callback.onSuccess(response)
                    }
                })
    }

    fun getPetsForAdoption(callback: PetsForAdoptionListCallback): Subscription {
        return service.petsForAdoption
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext{ Observable.error(it) }
                .subscribe(object : Subscriber<List<PetResponse>>() {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {
                        callback.onError(RestApiError(e))
                    }

                    override fun onNext(response: List<PetResponse>) {
                        callback.onSuccess(response)
                    }
                })
    }

    fun geOngLocations(callback: OngLocationsListCallback): Subscription {
        return service.geOngLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext { Observable.error(it) }
                .subscribe(object : Subscriber<List<LocationResponse>>() {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {
                        callback.onError(RestApiError(e))
                    }

                    override fun onNext(response: List<LocationResponse>) {
                        callback.onSuccess(response)
                    }
                })
    }

    fun sendAdoptionRequest(request: AdoptionRequest, callback: AdoptionRequestCallback): Subscription {
        return service.sendAdoptionRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext{ Observable.error(it) }
                .subscribe(object : Subscriber<Response<AdoptionRequest>>() {
                    override fun onCompleted() {}

                    override fun onError(e: Throwable) {
                        callback.onError(RestApiError(e))
                    }

                    override fun onNext(response: Response<AdoptionRequest>) {
                        callback.onSuccess(response)
                    }
                })
    }

    interface PetsForAdoptionListCallback {
        fun onSuccess(cityListResponse: List<PetResponse>)

        fun onError(networkError: RestApiError)
    }

    interface LostPetsListCallback {
        fun onSuccess(petListResponse: List<PetResponse>)

        fun onError(networkError: RestApiError)
    }

    interface OngLocationsListCallback {
        fun onSuccess(locationListResponse: List<LocationResponse>)

        fun onError(networkError: RestApiError)
    }

    interface AdoptionRequestCallback {
        fun onSuccess(locationListResponse: Response<AdoptionRequest>)

        fun onError(networkError: RestApiError)
    }
}