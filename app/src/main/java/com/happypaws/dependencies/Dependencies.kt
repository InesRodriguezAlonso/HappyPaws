package com.happypaws.dependencies

import com.happypaws.adoption.AdoptionActivity
import com.happypaws.home.basefragment.BaseFragment
import com.happypaws.onglocations.OngLocationsActivity
import com.happypaws.res.RestClientModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RestClientModule::class])
interface Dependencies {
    fun inject(ongLocationsActivity: OngLocationsActivity)

    fun inject(baseFragment: BaseFragment)

    fun inject(adoptionActivity: AdoptionActivity)
}