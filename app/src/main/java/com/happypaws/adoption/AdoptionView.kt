package com.happypaws.adoption

interface AdoptionView {

    fun formCompleted()

    fun isAddressValid(): Boolean

    fun isAboutYouValid(): Boolean

    fun isEmailValid(): Boolean

    fun isNameValid(): Boolean

    fun isPhoneNumberValid(): Boolean

    fun onFailure(errorMessage: String)

    fun requestAdoption()

    fun showAboutYouError()

    fun showAddressError()

    fun showEmailError()

    fun showNameError()

    fun showPhoneNumberError()
}