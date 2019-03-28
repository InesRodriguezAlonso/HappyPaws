package com.happypaws.res.model

import com.google.gson.annotations.SerializedName

class ApiResponse {
    @SerializedName("status")
    var status: String = ""

    constructor() {}

    constructor(status: String) {
        this.status = status
    }
}