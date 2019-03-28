package com.happypaws.home.adoption

import com.happypaws.home.basefragment.BaseView
import com.happypaws.res.model.PetResponse

interface AdoptionOverviewView : BaseView {

    fun getPetsForAdoptionListSuccess(response: List<PetResponse>)

}