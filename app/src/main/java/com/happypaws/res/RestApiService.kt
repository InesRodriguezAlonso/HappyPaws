package com.happypaws.res

import com.happypaws.res.model.AdoptionRequest
import com.happypaws.res.model.LocationResponse
import com.happypaws.res.model.PetResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import rx.Observable

interface RestApiService {

    @get:GET("adoptionList")
    val petsForAdoption: Observable<List<PetResponse>>

    @get:GET("lostList")
    val lostPets: Observable<List<PetResponse>>

    @GET("locationList")
    fun geOngLocations(): Observable<List<LocationResponse>>

    @POST("adoptionRequestList")
    fun sendAdoptionRequest(@Body request: AdoptionRequest): Observable<Response<AdoptionRequest>>
}