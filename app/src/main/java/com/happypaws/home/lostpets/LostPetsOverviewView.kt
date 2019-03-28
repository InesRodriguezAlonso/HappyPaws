package com.happypaws.home.lostpets

import com.happypaws.home.basefragment.BaseView
import com.happypaws.res.model.PetResponse

interface LostPetsOverviewView : BaseView {

    fun getLostPetsListSuccess(lostPetsListResponse: List<PetResponse>?)

}