package com.happypaws.res.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class LocationResponse : Serializable {

    @SerializedName("id")
    @Expose
    var id: Long? = null

    @SerializedName("lat")
    @Expose
    var latitude: Double? = null

    @SerializedName("long")
    @Expose
    var longitude: Double? = null

    @SerializedName("city")
    @Expose
    private var city: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    fun setCity(city: String) {
        this.city = city
    }
}