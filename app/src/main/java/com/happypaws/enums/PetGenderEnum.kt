package com.happypaws.enums

import androidx.annotation.DrawableRes
import com.happypaws.R

enum class PetGenderEnum(@param:DrawableRes private val iconRes: Int) {
    Undefined(R.drawable.ic_pet_undefined_20px),
    Female(R.drawable.ic_pet_female_20px),
    Male(R.drawable.ic_pet_male_20px);


    companion object {

        @DrawableRes
        fun getIconRes(genderType: Int): Int {
            return when (genderType) {
                1 -> Female.iconRes
                2 -> Male.iconRes
                else -> Undefined.iconRes
            }
        }
    }
}