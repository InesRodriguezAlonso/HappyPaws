package com.happypaws.enums

import androidx.annotation.DrawableRes
import com.happypaws.R

enum class PetTypeEnum(@param:DrawableRes private val iconRes: Int) {
    Undefined(R.drawable.ic_pet_undefined_20px),
    Cat(R.drawable.ic_cat),
    Dog(R.drawable.ic_dog),
    Bunny(R.drawable.ic_bunny);


    companion object {

        fun getIconRes(genderType: Int): Int {
            return when (genderType) {
                1 -> Cat.iconRes
                2 -> Dog.iconRes
                3 -> Bunny.iconRes
                else -> Undefined.iconRes
            }
        }
    }
}
