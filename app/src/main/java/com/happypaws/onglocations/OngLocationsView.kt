package com.happypaws.onglocations

import com.happypaws.res.model.LocationResponse

interface OngLocationsView {

    fun getOngLocationsListSuccess(lostPetsListResponse: List<LocationResponse>)

    fun onFailure(errorMessage: String)
}