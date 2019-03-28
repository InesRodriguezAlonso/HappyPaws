package com.happypaws.adoption

import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent

import rx.functions.Action1
import rx.functions.Func1
import rx.functions.Func5

interface AdoptionPresenterImpl {
    fun isNameValid(): Func1<TextViewAfterTextChangeEvent, Boolean>

    fun isAddressValid(): Func1<TextViewAfterTextChangeEvent, Boolean>

    fun isEmailValid(): Func1<TextViewAfterTextChangeEvent, Boolean>

    fun isPhoneNumberValid(): Func1<TextViewAfterTextChangeEvent, Boolean>

    fun isAboutYouValid(): Func1<TextViewAfterTextChangeEvent, Boolean>

    fun isFormValid(): Func5<Boolean, Boolean, Boolean, Boolean, Boolean, Boolean>

    fun updateNameViewState(): Action1<Boolean>

    fun updateAddressViewState(): Action1<Boolean>

    fun updateEmailViewState(): Action1<Boolean>

    fun updatePhoneNumberViewState(): Action1<Boolean>

    fun updateAboutYouViewState(): Action1<Boolean>
}