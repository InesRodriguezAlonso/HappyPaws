package com.happypaws.res.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AdoptionRequest(name: String, @field:SerializedName("applicantAddress")
@field:Expose
var address: String?, @field:SerializedName("applicantEmail")
                      @field:Expose
                      var email: String?, phoneNumber: String, @field:SerializedName("applicantDescription")
                      @field:Expose
                      var description: String?, @field:SerializedName("petId")
                      @field:Expose
                      var petId: Long?) {
    @SerializedName("applicantName")
    @Expose
    var name: String? = null

    @SerializedName("applicantPhoneNumber")
    @Expose
    var phoneNumber: String? = null

    init {
        this.name = name
        this.name = phoneNumber
    }
}