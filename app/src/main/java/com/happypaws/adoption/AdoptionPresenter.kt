package com.happypaws.adoption

import android.util.Log

import com.happypaws.res.RestApi
import com.happypaws.res.RestApiError
import com.happypaws.res.model.AdoptionRequest
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent

import retrofit2.Response
import rx.functions.Action1
import rx.functions.Func1
import rx.functions.Func5
import rx.subscriptions.CompositeSubscription

class AdoptionPresenter(private val service: RestApi?, private val view: AdoptionView) : AdoptionPresenterImpl {
    private val subscriptions: CompositeSubscription = CompositeSubscription()

    fun sendAdoptionRequest(request: AdoptionRequest) {
        val subscription = service!!.sendAdoptionRequest(request, object : RestApi.AdoptionRequestCallback {
            override fun onSuccess(locationListResponse: Response<AdoptionRequest>) {
                view.formCompleted()
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

    override fun isNameValid(): Func1<TextViewAfterTextChangeEvent, Boolean> {
        return Func1 { view.isNameValid() }
    }

    override fun updateNameViewState(): Action1<Boolean> {
        return Action1 { aBoolean ->
            if (!aBoolean) {
                view.showNameError()
            }
        }
    }

    override fun isAddressValid(): Func1<TextViewAfterTextChangeEvent, Boolean> {
        return Func1 { view.isAddressValid() }
    }

    override fun updateAddressViewState(): Action1<Boolean> {
        return Action1 { aBoolean ->
            if (!aBoolean) {
                view.showAddressError()
            }
        }
    }

    override fun isEmailValid(): Func1<TextViewAfterTextChangeEvent, Boolean> {
        return Func1 { view.isEmailValid() }
    }

    override fun updateEmailViewState(): Action1<Boolean> {
        return Action1 { aBoolean ->
            if (!aBoolean) {
                view.showEmailError()
            }
        }
    }

    override fun isPhoneNumberValid(): Func1<TextViewAfterTextChangeEvent, Boolean> {
        return Func1 { view.isPhoneNumberValid() }
    }

    override fun updatePhoneNumberViewState(): Action1<Boolean> {
        return Action1 { aBoolean ->
            if (!aBoolean) {
                view.showPhoneNumberError()
            }
        }
    }

    override fun isAboutYouValid(): Func1<TextViewAfterTextChangeEvent, Boolean> {
        return Func1 { view.isAboutYouValid() }
    }

    override fun updateAboutYouViewState(): Action1<Boolean> {
        return Action1 { aBoolean ->
            if (!aBoolean) {
                view.showAboutYouError()
            }
        }
    }

    override fun isFormValid(): Func5<Boolean, Boolean, Boolean, Boolean, Boolean, Boolean> {
        return Func5 { aBoolean, aBoolean2, aBoolean3, aBoolean4, aBoolean5 ->
            (aBoolean!!
                    && aBoolean2!!
                    && aBoolean3!!
                    && aBoolean4!!
                    && aBoolean5!!)
        }
    }

    companion object {

        var TAG = "AdoptionPresenter"
    }
}