package com.happypaws.res.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.happypaws.App
import com.happypaws.enums.PetGenderEnum
import com.happypaws.enums.PetTypeEnum
import com.happypaws.utils.TimeUtils

import java.io.Serializable

import androidx.annotation.DrawableRes

class PetResponse : Serializable {

    @SerializedName("id")
    @Expose
    var petId: Long? = null

    @SerializedName("name")
    @Expose
    var petName: String? = null

    @SerializedName("ageMonths")
    @Expose
    var petAgeMonths: Long? = null

    @SerializedName("breed")
    @Expose
    var petBreed: String? = null

    @SerializedName("type")
    @Expose
    private var petType: Int = 0

    @SerializedName("gender")
    @Expose
    private var petGender: Int = 0

    @SerializedName("description")
    @Expose
    var petDescription: String? = null

    @SerializedName("registrationDate")
    @Expose
    var petRegistrationDateMs: Long? = null

    @SerializedName("profileThumbnailUrl")
    @Expose
    var petProfileThumbnailUrl: String? = null

    val petAge: String
        get() = TimeUtils.getFormattedAge(petAgeMonths, App.appInstance!!.applicationContext)

    val petTypeIcon: Int
        @DrawableRes
        get() = PetTypeEnum.getIconRes(petType)

    val petGenderIcon: Int
        @DrawableRes
        get() = PetGenderEnum.getIconRes(petGender)
}